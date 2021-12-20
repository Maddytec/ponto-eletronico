package br.com.maddytec.pontoeletronico.dtos

import br.com.maddytec.pontoeletronico.documents.Empresa

data class EmpresaResponseDto (
    val id: String? = "",
    val razaoSocial: String = "",
    val cnpj: String = ""
) {
    companion object {
        fun ofEntity(empresa: Empresa) = EmpresaResponseDto(
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
