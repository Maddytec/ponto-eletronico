package br.com.maddytec.pontoeletronico.dtos

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


data class FuncionarioDto(
    val id: String? = null,

    @get:NotEmpty(message = "Nome é obrigatório.")
    @get:Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
    val nome: String = "",

    @get:NotEmpty(message = "Email é obrigatório.")
    @get:Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @get:Email(message = "Email inválido.")
    val email: String = "",

    val senha: String? = null,
    val valorHora: String? = null,
    val quantidadeHorasTrabalhadaDia: String? = null,
    val quantidadeHorasAlmoco: String? = null

)
