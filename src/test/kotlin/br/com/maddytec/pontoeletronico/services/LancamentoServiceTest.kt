package br.com.maddytec.pontoeletronico.services;

import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.enums.TipoEnum
import br.com.maddytec.pontoeletronico.repository.LancamentoRepository;
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
class LancamentoServiceTest(@Autowired val lancamentoService: LancamentoService ) {

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    private val ID_FUNCIONARIO = "1"
    private val ID_LANCAMENTO = "1"

    @BeforeEach
    fun setUp(){
        BDDMockito.given<Page<Lancamento>>(lancamentoRepository?.findByFuncionarioId(ID_FUNCIONARIO, PageRequest.of(0,5)))
            .willReturn(PageImpl(ArrayList<Lancamento>()))
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java))).willReturn(lancamento())
        BDDMockito.given(lancamentoRepository?.findById(ID_LANCAMENTO)).willReturn(Optional.of(lancamento()))
    }

    @Test
    fun buscarPorFuncionarioIdTest(){
       val lancamento = lancamentoService.buscarPorFuncionarioId(ID_FUNCIONARIO, PageRequest.of(0,5))
       Assertions.assertNotNull(lancamento)
    }

    @Test
    fun salvarTest(){
        val lancamento: Lancamento = lancamentoService.salvar(lancamento())
        Assertions.assertNotNull(lancamento)
    }

    @Test
    fun buscarPorIdTest(){
        val lancamento: Lancamento? = lancamentoService.buscarPorId(ID_LANCAMENTO)
        Assertions.assertNotNull(lancamento)
    }

    private fun lancamento(): Lancamento {
        return Lancamento(
            LocalDate.now(),
            TipoEnum.INICIO_TRABALHO,
            "1"
        )
    }

}
