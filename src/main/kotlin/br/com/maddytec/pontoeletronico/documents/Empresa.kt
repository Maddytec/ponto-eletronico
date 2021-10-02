package br.com.maddytec.pontoeletronico.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Empresa(
    @Id
    val id: String? = null,
    val razaoSocial: String,
    val cnpj: String
)