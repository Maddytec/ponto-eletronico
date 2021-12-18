package br.com.maddytec.pontoeletronico.dtos

import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.enums.TipoEnum
import br.com.maddytec.pontoeletronico.utils.DateUtils
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty

data class LancamentoDto(
    @get:NotEmpty(message = "Data é obrigatório.")
    val data: String? = null,

    @get:NotEmpty(message = "Tipo é obrigatório.")
    val tipo: String? = null,

    val descricao: String? = null,
    val localizacao: String? = null,
    val funcionarioId: String? = null
) {
    companion object {
        fun ofEntity(lancamento: Lancamento) = LancamentoDto (
            data = lancamento.data?.let { DateUtils.localDateTimeToStringBr(it) },
            tipo = lancamento.tipo.toString(),
            funcionarioId = lancamento.funcionarioId,
            descricao = lancamento.descricao,
            localizacao = lancamento.localizacao
        )
    }

    fun toEntity(): Lancamento {
        return Lancamento(
            data = LocalDateTime.parse(data, DateUtils.formmaterDateHourBr),
            tipo = TipoEnum.valueOf(tipo.toString()),
            funcionarioId = funcionarioId,
            descricao = descricao,
            localizacao = localizacao
        )
    }

}