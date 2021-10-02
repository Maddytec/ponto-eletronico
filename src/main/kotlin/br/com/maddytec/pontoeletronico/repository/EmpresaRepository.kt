package br.com.maddytec.pontoeletronico.repository

import br.com.maddytec.pontoeletronico.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpresaRepository : MongoRepository<Empresa, String> {

    fun findByCnpj(cnpj: String): Empresa?
}