package br.com.maddytec.pontoeletronico.documents

import br.com.maddytec.pontoeletronico.enums.TipoEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Lancamento (
    @Id
    val id: String? = null,
    val data: Date,
    val tipo: TipoEnum,
    val funcionarioId: String,
    val descricao: String? = "",
    val localizacao: String? = ""
)