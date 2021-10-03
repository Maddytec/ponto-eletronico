package br.com.maddytec.pontoeletronico.services.impl

import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.repository.LancamentoRepository
import br.com.maddytec.pontoeletronico.services.LancamentoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository)  : LancamentoService {
    override fun buscarPorFuncionarioId(funcionarioId: String, pageRequest: PageRequest) = lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest)

    override fun buscarPorId(id: String): Lancamento? = lancamentoRepository.findById(id).orElse(null)

    override fun salvar(lancamento: Lancamento): Lancamento = lancamentoRepository.save(lancamento)

    override fun remover(id: String) {
        lancamentoRepository.deleteById(id)
    }
}