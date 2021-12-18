package br.com.maddytec.pontoeletronico.services

import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.dtos.LancamentoDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface LancamentoService {

    fun buscarPorFuncionarioId( funcionarioId: String, pageRequest: PageRequest): Page<Lancamento>

    fun buscarPorId(id: String): LancamentoDto?

    fun salvar(lancamento: LancamentoDto): LancamentoDto

    fun remover(id: String)

    fun atualizar(lacamentoId: String, lancamentoDto: LancamentoDto)
}