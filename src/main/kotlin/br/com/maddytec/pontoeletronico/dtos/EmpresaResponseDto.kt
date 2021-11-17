package br.com.maddytec.pontoeletronico.dtos

data class EmpresaResponseDto (
    val id: String? = "",
    val razaoSocial: String = "",
    val cnpj: String = ""
)
