package br.com.maddytec.pontoeletronico.services;

import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.dtos.LancamentoDto
import br.com.maddytec.pontoeletronico.enums.TipoEnum
import br.com.maddytec.pontoeletronico.repository.LancamentoRepository;
import br.com.maddytec.pontoeletronico.utils.DateUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
open class LancamentoServiceTest {

    @Autowired
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    private val ID_FUNCIONARIO = "1"
    private val ID_LANCAMENTO = "1"

    @BeforeEach
    fun setUp(){
        `when`(lancamentoRepository?.findByFuncionarioId(ID_FUNCIONARIO, PageRequest.of(0,5)))
            .thenReturn(PageImpl(ArrayList<Lancamento>()))
        `when`(lancamentoRepository?.save(any(Lancamento::class.java))).thenReturn(lancamento())
        `when`(lancamentoRepository?.findById(ID_LANCAMENTO)).thenReturn(Optional.of(lancamento()))
    }

    @Test
    fun buscarPorFuncionarioIdTest(){
       val lancamento = lancamentoService?.buscarPorFuncionarioId(ID_FUNCIONARIO, PageRequest.of(0,5))
       Assertions.assertNotNull(lancamento)
    }

    @Test
    fun salvarTest(){
        val lancamentoDto: LancamentoDto? = lancamentoService?.salvar(lancamentoDto())
        Assertions.assertNotNull(lancamentoDto)
    }

    @Test
    fun buscarPorIdTest(){
        val lancamento: LancamentoDto? = lancamentoService?.buscarPorId(ID_LANCAMENTO)
        Assertions.assertNotNull(lancamento)
    }

    private fun lancamentoDto(): LancamentoDto {
        return LancamentoDto(
            data = DateUtils.localDateTimeToStringBr(LocalDateTime.now()),
            tipo = TipoEnum.INICIO_TRABALHO.name.toString(),
            descricao = null,
            localizacao = null,
            funcionarioId = ID_FUNCIONARIO
        )
    }

    private fun lancamento(): Lancamento {
        return Lancamento(
            data = LocalDateTime.now(),
            tipo = TipoEnum.INICIO_TRABALHO,
            descricao = "",
            localizacao = "",
            funcionarioId = ID_FUNCIONARIO
        )
    }

}
