package br.com.maddytec.pontoeletronico.services

import br.com.maddytec.pontoeletronico.documents.Empresa
import br.com.maddytec.pontoeletronico.dtos.EmpresaRequestDto
import br.com.maddytec.pontoeletronico.dtos.EmpresaResponseDto

interface EmpresaService {

    fun buscarPorCnpj(cnpj: String): Empresa?

    fun salvar(empresaRequestDto: EmpresaRequestDto): EmpresaResponseDto

    fun get(): List<Empresa>
}