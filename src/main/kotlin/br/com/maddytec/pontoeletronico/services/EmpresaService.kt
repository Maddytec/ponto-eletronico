package br.com.maddytec.pontoeletronico.services

import br.com.maddytec.pontoeletronico.documents.Empresa

interface EmpresaService {

    fun buscarPorCnpj(cnpj: String): Empresa?

    fun salvar(empresa: Empresa): Empresa

    fun get(): List<Empresa>
}