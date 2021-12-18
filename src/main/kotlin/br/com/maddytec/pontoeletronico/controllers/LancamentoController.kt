package br.com.maddytec.pontoeletronico.controllers

import br.com.maddytec.pontoeletronico.documents.Funcionario
import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.dtos.LancamentoDto
import br.com.maddytec.pontoeletronico.enums.TipoEnum
import br.com.maddytec.pontoeletronico.exception.NotFoundExceptionHandle
import br.com.maddytec.pontoeletronico.response.Response
import br.com.maddytec.pontoeletronico.services.FuncionarioService
import br.com.maddytec.pontoeletronico.services.LancamentoService
import br.com.maddytec.pontoeletronico.utils.DateUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("api/lancamento")
class LancamentoController(val lancamentoService: LancamentoService, val funcionarioService: FuncionarioService) {

    @Value("\${paginacao.quantidade-por-pagina}")
    val quantidadePorPagina: Int = 15


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun buscarPorId(@PathVariable id: String): Response<LancamentoDto> {
        val response: Response<LancamentoDto> = Response<LancamentoDto>()

        val lancamentoDto = lancamentoService.buscarPorId(id)

        if (Objects.isNull(lancamentoDto)) {
            throw NotFoundExceptionHandle("Lançamento não encontrado.")
        }

        response.data = lancamentoDto
        return response
    }

    @GetMapping("/funcionario/{funcionarioId}")
    fun buscarPorFuncionarioId(
        @PathVariable funcionarioId: String,
        @RequestParam(value = "pag", defaultValue = "0") pag: Int,
        @RequestParam(value = "ord", defaultValue = "id") ord: String,
        @RequestParam(value = "dir", defaultValue = "DESC") dir: String
    ): ResponseEntity<Response<Page<LancamentoDto>>>? {

        val response = Response<Page<LancamentoDto>>()

        val pageRequest = PageRequest.of(pag, quantidadePorPagina, Sort.Direction.valueOf(dir), ord)

        val lancamentos: Page<Lancamento> =
            lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest)

        val lancamentosDto: Page<LancamentoDto> = lancamentos.map { converteLancamentoDto(it) }

        response.data = lancamentosDto
        return ResponseEntity.ok(response)

    }

    @PostMapping
    fun adicionar(
        @Valid @RequestBody lancamentoDto: LancamentoDto,
        result: BindingResult
    ): ResponseEntity<Response<LancamentoDto>> {

        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)

        if (isResponseError(result, response)) {
            return ResponseEntity.badRequest().body(response)
        }

        response.data = lancamentoService.salvar(lancamentoDto)
        return ResponseEntity.ok(response)
    }

    private fun isResponseError(
        result: BindingResult,
        response: Response<LancamentoDto>
    ): Boolean {
        if (result.hasErrors()) {
            result.allErrors.map { erro ->
                erro.defaultMessage?.let { response.erros.add(it) }
            }
            return true
        }
        return false
    }

    private fun validarFuncionario(lancamentoDto: LancamentoDto, result: BindingResult) {
        if (lancamentoDto.funcionarioId == null) {
            result.addError(ObjectError("funcionario", "Funcionario não informado."))
            return
        }

        val funcionario: Funcionario? = funcionarioService.buscarPorId(lancamentoDto.funcionarioId!!)

        funcionario ?: result.addError(
            ObjectError(
                "funcionario",
                "Funcionário não encontrado, id inexistente."
            )
        )

    }

    private fun converteLancamentoDto(lancamento: Lancamento?): LancamentoDto? =
         lancamento?.let {
            LancamentoDto(
                it.data?.format(DateUtils.formmaterDateHourBr),
                it.tipo?.name,
                it.descricao,
                it.localizacao,
                it.funcionarioId
            )
        }

    private fun validarLancamentoPorId(lancamentoId: String?, bindingResult: BindingResult){
            val lancamento: LancamentoDto? = lancamentoId?.let { lancamentoService.buscarPorId(it) }
            if (lancamento == null ){
                bindingResult.addError(ObjectError("lancamento", "Lancamento não encontrado."))
            }
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable lancamentoId: String, @RequestBody lancamentoDto: LancamentoDto,
        result: BindingResult ){

        lancamentoService.atualizar(lancamentoId,lancamentoDto)
    }
}