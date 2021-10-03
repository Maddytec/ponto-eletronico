package br.com.maddytec.pontoeletronico.services.impl

import br.com.maddytec.pontoeletronico.documents.Funcionario
import br.com.maddytec.pontoeletronico.repository.FuncionarioRepository
import br.com.maddytec.pontoeletronico.services.FuncionarioService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FuncionarioServiceImpl(val funcionarioRepository: FuncionarioRepository) : FuncionarioService {

    override fun salvar(funcionario: Funcionario): Funcionario = funcionarioRepository.save(funcionario)

    override fun buscarPorCpf(cpf: String) = funcionarioRepository.findByCpf(cpf)

    override fun buscarPorEmail(email: String) = funcionarioRepository.findByEmail(email)

    override fun buscarPorId(id: String) = funcionarioRepository.findByIdOrNull(id)
}