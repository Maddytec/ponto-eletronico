package br.com.maddytec.pontoeletronico.dtos

data class EmpresaDto(
    val id: String? = null,
    val razaoSocial: String,
    val cnpj: String
)
