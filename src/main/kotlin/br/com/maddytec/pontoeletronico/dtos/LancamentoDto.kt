package br.com.maddytec.pontoeletronico.dtos

import javax.validation.constraints.NotEmpty

data class LancamentoDto(
    @get:NotEmpty(message = "Data é obrigatório.")
    val data: String? = null,

    @get:NotEmpty(message = "Tipo é obrigatório.")
    val tipo: String? = null,

    val descricao: String? = null,
    val localizacao: String? = null,
    val funcionarioId: String? = null,
    val id: String? = null,
)
