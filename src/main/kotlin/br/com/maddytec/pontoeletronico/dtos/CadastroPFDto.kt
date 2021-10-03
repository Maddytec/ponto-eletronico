package br.com.maddytec.pontoeletronico.dtos

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CadastroPFDto(
    val id: String? = null,

    @get:NotEmpty(message = "Nome é obrigatório.")
    @get:Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
    val nome: String = "",

    @get:NotEmpty(message = "Email é obrigatório.")
    @get:Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @get:Email(message = "Email inválido.")
    val email: String = "",

    @get:NotEmpty(message = "Senha é obrigatório.")
    val senha: String = "",

    @get:NotEmpty(message = "CPF é obrigatório.")
    @get:CPF(message = "CPF inválido.")
    val cpf: String = "",

    @get:NotEmpty(message = "CNPJ é obrigatório.")
    @get:CNPJ(message = "CNPJ inválido.")
    val cnpj: String = "",

    val empresaId: String = "",
    val valorHora: String? = null,
    val quantidadeHorasTrabalhoDia: String? = null,
    val quantidadeHorasAlmoco: String? = null

)
