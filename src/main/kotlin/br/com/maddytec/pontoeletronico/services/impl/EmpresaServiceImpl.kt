package br.com.maddytec.pontoeletronico.services.impl

import br.com.maddytec.pontoeletronico.documents.Empresa
import br.com.maddytec.pontoeletronico.dtos.EmpresaRequestDto
import br.com.maddytec.pontoeletronico.dtos.EmpresaResponseDto
import br.com.maddytec.pontoeletronico.repository.EmpresaRepository
import br.com.maddytec.pontoeletronico.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {

    override fun buscarPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)

    override fun salvar(empresaRequestDto: EmpresaRequestDto) =
        EmpresaResponseDto.ofEntity(empresaRepository.save(empresaRequestDto.toEntity()))

    override fun get(): List<Empresa> = empresaRepository.findAll()
}