package br.com.maddytec.pontoeletronico.documents

import br.com.maddytec.pontoeletronico.enums.TipoEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Document
data class Lancamento(

    var data: LocalDateTime?,
    val tipo: TipoEnum?,
    val funcionarioId: String?,
    val descricao: String? = "",
    val localizacao: String? = "",
    @Id
    val id: String? = "",
)