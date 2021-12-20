package br.com.maddytec.pontoeletronico.dtos

import br.com.maddytec.pontoeletronico.documents.Empresa
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import kotlin.math.max
import kotlin.math.min

data class EmpresaRequestDto(
    val id: String? = null,

    @get:NotEmpty(message = "Razão Social é obrigatória.")
    @get:Length(min = 5, max = 200, message = "Razão Social deve possuir entre 5 e 200 caracteres.")
    val razaoSocial: String,

    @get:NotEmpty(message = "CNPJ é obrigatório.")
    @get:CNPJ(message = "CNPJ invalido.")
    val cnpj: String
) {
    companion object {
        fun ofEntity(empresa: Empresa) = EmpresaRequestDto(
            id = empresa.id,
            razaoSocial = empresa.razaoSocial,
            cnpj = empresa.cnpj
        )
    }

    fun toEntity(): Empresa {
        return Empresa(
            id = id,
            razaoSocial = razaoSocial,
            cnpj = cnpj
        )
    }
}
