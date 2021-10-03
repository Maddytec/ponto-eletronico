package br.com.maddytec.pontoeletronico.dtos

import javax.validation.constraints.NotEmpty

data class LancamentoDto(
    val id: String? = null,

    @get:NotEmpty(message = "Data é obrigatório.")
    val data: String? = null,

    @get:NotEmpty(message = "Tipo é obrigatório.")
    val tipo: String? = null,

    val descricao: String? = null,
    val localizacao: String? = null,
    val funcionarioId: String? = null
)
