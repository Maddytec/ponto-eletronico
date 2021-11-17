package br.com.maddytec.pontoeletronico.controllers

import br.com.maddytec.pontoeletronico.documents.Funcionario
import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.dtos.LancamentoDto
import br.com.maddytec.pontoeletronico.enums.TipoEnum
import br.com.maddytec.pontoeletronico.response.Response
import br.com.maddytec.pontoeletronico.services.FuncionarioService
import br.com.maddytec.pontoeletronico.services.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("api/lancamento")
class LancamentoController(val lancamentoService: LancamentoService, val funcionarioService: FuncionarioService) {

    val formmater = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    @Value("\${paginacao.quantidade-por-pagina}")
    val quantidadePorPagina: Int = 15


    @GetMapping("/{id}")
    fun buscarPorId( @PathVariable id: String): ResponseEntity<Response<LancamentoDto>>{
        val response: Response<LancamentoDto> = Response<LancamentoDto>()

        val lancamento = lancamentoService.buscarPorId(id)

        if(Objects.isNull(lancamento)) {
            response.erros.add("Lançamento não encontrado.")
            return ResponseEntity.badRequest().body(response)
        }

        val lancamentoDto = converteLancamentoDto(lancamento as Lancamento)
        response.data = lancamentoDto
        return ResponseEntity.ok(response)
    }

    @GetMapping("/funcionario/{funcionarioId}")
    fun buscarPorFuncionarioId(@PathVariable funcionarioId: String,
                               @RequestParam(value = "pag", defaultValue = "0" ) pag: Int,
                               @RequestParam(value = "ord", defaultValue = "id") ord: String,
                               @RequestParam(value = "dir", defaultValue = "DESC") dir: String
    ): ResponseEntity<Response<Page<LancamentoDto>>>? {

        val response = Response<Page<LancamentoDto>>()

        val pageRequest = PageRequest.of(pag, quantidadePorPagina, Sort.Direction.valueOf(dir), ord )

        val lancamentos: Page<Lancamento> = lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest)

        val lancamentosDto: Page<LancamentoDto> = lancamentos.map { converteLancamentoDto(it) }

        response.data = lancamentosDto
        return ResponseEntity.ok(response)

    }

    @PostMapping
    fun adicionar(
        @Valid @RequestBody lancamentoDto: LancamentoDto,
        result: BindingResult ) : ResponseEntity<Response<LancamentoDto>> {

        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)

        if (result.hasErrors()) {
            result.allErrors.map { erro ->
                erro.defaultMessage?.let { response.erros.add(it) }
                return ResponseEntity.badRequest().body(response)
            }
        }

        val lancamento: Lancamento = converterDtoParaLancamento(lancamentoDto, result)
        lancamentoService.salvar(lancamento)
        response.data = converteLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    private fun validarFuncionario(lancamentoDto: LancamentoDto, result: BindingResult) {
        if(lancamentoDto.funcionarioId == null ){
            result.addError(ObjectError("funcionario","Funcionario não informado."))
            return
        }

        val funcionario: Funcionario? = funcionarioService.buscarPorId(lancamentoDto.funcionarioId)

        funcionario ?: result.addError(ObjectError("funcionario", "Funcionário não encontrado, id inexistente."))

    }

    private fun converteLancamentoDto(lancamento: Lancamento): LancamentoDto =
        LancamentoDto(lancamento.data.format(formmater), lancamento.tipo.name, lancamento.descricao, lancamento.localizacao, lancamento.id)

    private fun converterDtoParaLancamento(lancamentoDto: LancamentoDto, result: BindingResult): Lancamento {
        if(lancamentoDto.id != null ){
            val lancamento: Lancamento? = lancamentoService.buscarPorId(lancamentoDto.id)
            if (lancamento == null ){
                result.addError(ObjectError("funcionario", "Funcionário não encontrado."))
            }
        }

        val lancamento: Lancamento = Lancamento(
            LocalDate.parse(lancamentoDto.data, formmater),
            TipoEnum.valueOf(lancamentoDto.id!!),
            lancamentoDto.funcionarioId!!,
            lancamentoDto.descricao,
            lancamentoDto.localizacao
            )
        return lancamento
    }
}