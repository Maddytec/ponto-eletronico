package br.com.maddytec.pontoeletronico.controllers

import br.com.maddytec.pontoeletronico.documents.Empresa
import br.com.maddytec.pontoeletronico.dtos.EmpresaDto
import br.com.maddytec.pontoeletronico.dtos.EmpresaResponseDto
import br.com.maddytec.pontoeletronico.services.EmpresaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/empresa")
class EmpresaController(val empresaService: EmpresaService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody empresaDto: EmpresaDto): EmpresaResponseDto {
        var empresa = empresaService.salvar(Empresa(empresaDto.razaoSocial, empresaDto.cnpj))
        return EmpresaResponseDto(empresa.id, empresa.razaoSocial, empresa.cnpj)
    }

    @GetMapping
    fun get() = empresaService.get()

}