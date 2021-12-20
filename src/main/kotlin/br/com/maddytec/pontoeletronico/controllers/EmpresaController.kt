package br.com.maddytec.pontoeletronico.controllers

import br.com.maddytec.pontoeletronico.dtos.EmpresaRequestDto
import br.com.maddytec.pontoeletronico.services.EmpresaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/empresa")
class EmpresaController(val empresaService: EmpresaService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody empresaRequestDto: EmpresaRequestDto) = empresaService.salvar(empresaRequestDto)

    @GetMapping
    fun get() = empresaService.get()

}