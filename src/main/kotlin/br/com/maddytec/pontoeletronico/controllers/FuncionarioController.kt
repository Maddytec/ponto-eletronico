package br.com.maddytec.pontoeletronico.controllers

import br.com.maddytec.pontoeletronico.documents.Funcionario
import br.com.maddytec.pontoeletronico.dtos.FuncionarioDto
import br.com.maddytec.pontoeletronico.dtos.FuncionarioResponseDto
import br.com.maddytec.pontoeletronico.enums.PerfilEnum
import br.com.maddytec.pontoeletronico.response.Response
import br.com.maddytec.pontoeletronico.services.FuncionarioService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter
import javax.validation.Valid

@RestController
@RequestMapping("api/funcionario")
class FuncionarioController(val funcionarioService: FuncionarioService) {

    val formmater = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    @Value("\${paginacao.quantidade-por-pagina}")
    val quantidadePorPagina: Int = 15


    @PostMapping
    fun save(
        @Valid @RequestBody funcionarioDto: FuncionarioDto,
        result: BindingResult ) : ResponseEntity<Response<FuncionarioResponseDto>> {

        val response: Response<FuncionarioResponseDto> = Response<FuncionarioResponseDto>()

        if (result.hasErrors()) {
            result.allErrors.map { erro ->
                erro.defaultMessage?.let { response.erros.add(it) }
                return ResponseEntity.badRequest().body(response)
            }
        }

        val funcionario: Funcionario = Funcionario(
            funcionarioDto.nome,
            funcionarioDto.email,
            funcionarioDto.senha?.let { it }.toString(),
            funcionarioDto.cpf,
            PerfilEnum.ROLE_ADMIN,
            funcionarioDto.empresaId)

        funcionarioService.salvar(funcionario)
        response.data = converteFuncionarioResponseDto(funcionario)
        return ResponseEntity.ok(response)
    }

    private fun converteFuncionarioResponseDto(funcionario: Funcionario): FuncionarioResponseDto =
        FuncionarioResponseDto(funcionario.nome,funcionario.email, funcionario.cpf)

    @GetMapping
    fun get() = funcionarioService.get()
}