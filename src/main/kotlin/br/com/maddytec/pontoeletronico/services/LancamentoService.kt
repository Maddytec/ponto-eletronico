package br.com.maddytec.pontoeletronico.services

import br.com.maddytec.pontoeletronico.documents.Lancamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface LancamentoService {

    fun buscarPorFuncionarioId( funcionarioId: String, pageRequest: PageRequest): Page<Lancamento>

    fun buscarPorId(id: String): Lancamento?

    fun salvar(lancamento: Lancamento): Lancamento

    fun remover(id: String)
}