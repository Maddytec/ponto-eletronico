package br.com.maddytec.pontoeletronico.services.impl

import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.dtos.LancamentoDto
import br.com.maddytec.pontoeletronico.repository.LancamentoRepository
import br.com.maddytec.pontoeletronico.services.LancamentoService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository)  : LancamentoService {

    override fun buscarPorFuncionarioId(funcionarioId: String, pageRequest: PageRequest) = lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest)

    override fun buscarPorId(id: String): LancamentoDto? {
        return LancamentoDto.ofEntity(lancamentoRepository.findById(id).orElse(null))
    }
    override fun salvar(lancamentoDto: LancamentoDto): LancamentoDto {
        var lancamento: Lancamento = lancamentoDto.toEntity()
        lancamentoRepository.save(lancamento)
        return LancamentoDto.ofEntity(lancamento)
    }

    override fun remover(id: String) {
        lancamentoRepository.deleteById(id)
    }

    override fun atualizar(lacamentoId: String, lancamentoDto: LancamentoDto) {
        lancamentoRepository.findById(lacamentoId).orElseThrow { throw Exception("Lançamento não encontrado") }
        val lancamento: Lancamento =  lancamentoDto.toEntity()
        lancamentoRepository.save(lancamento)
    }
}