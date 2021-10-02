package br.com.maddytec.pontoeletronico.services.impl

import br.com.maddytec.pontoeletronico.documents.Empresa
import br.com.maddytec.pontoeletronico.repository.EmpresaRepository
import br.com.maddytec.pontoeletronico.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {

    override fun buscarPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)

    override fun salvar(empresa: Empresa): Empresa = empresaRepository.save(empresa)
}