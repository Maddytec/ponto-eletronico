package br.com.maddytec.pontoeletronico.repository

import br.com.maddytec.pontoeletronico.documents.Funcionario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FuncionarioRepository : MongoRepository<Funcionario, String>  {

    fun findByEmail(email: String): Funcionario?

    fun findByCpf(cpf: String): Funcionario?
}