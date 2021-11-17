package br.com.maddytec.pontoeletronico.services

import br.com.maddytec.pontoeletronico.documents.Funcionario

interface FuncionarioService {

    fun salvar(funcionario: Funcionario): Funcionario

    fun buscarPorCpf(cpf: String): Funcionario?

    fun buscarPorEmail(email: String): Funcionario?

    fun buscarPorId(id: String): Funcionario?

    fun get(): List<Funcionario>

}