package br.com.maddytec.pontoeletronico

import br.com.maddytec.pontoeletronico.documents.Funcionario
import br.com.maddytec.pontoeletronico.documents.Lancamento
import br.com.maddytec.pontoeletronico.dtos.LancamentoDto
import br.com.maddytec.pontoeletronico.enums.PerfilEnum
import br.com.maddytec.pontoeletronico.enums.TipoEnum
import br.com.maddytec.pontoeletronico.services.FuncionarioService
import br.com.maddytec.pontoeletronico.services.LancamentoService
import br.com.maddytec.pontoeletronico.utils.DateUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.assertj.core.api.BDDAssumptions.given
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LancamentoControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    companion object {
        private val URL_BASE = "/api/lancamento/"
        private val EMPRESA_ID = "1"
        private val FUNCIONARIO_ID = "1"
        private val LANCAMENTO_ID = "1"
        private val TIPO_INICIO_TRABALHO = TipoEnum.INICIO_TRABALHO.name
        private val DATA_TIME = LocalDateTime.now()
        private val EMPLOYEE_NAME = "Madson Silva"
        private val EMAIL = "maddytec@gmail.com"
        private val SENHA = "abc123"
        private val CPF = "12345678910"
    }

    @Test
    @Throws(Exception::class)
    fun cadastrarLancamentoTest() {

        `when`(funcionarioService?.buscarPorId(FUNCIONARIO_ID)).thenReturn(getFuncionario())
        `when`(lancamentoService?.salvar(lancamentoDto())).thenReturn(lancamentoDto())

        mvc!!.perform(
            MockMvcRequestBuilders.post(URL_BASE)
                .content(getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.tipo").value(TIPO_INICIO_TRABALHO))
            .andExpect(jsonPath("$.data.data").value(DATA_TIME.format(DateUtils.formmaterDateHourBr)))
            .andExpect(jsonPath("$.data.funcionarioId").value(FUNCIONARIO_ID))
            .andExpect(jsonPath("$.erros").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    fun cadastrarLancamentoComUsuarioInvalidoTest() {

        `when`(funcionarioService?.buscarPorId(FUNCIONARIO_ID)).thenReturn(null)

        mvc!!.perform(
            MockMvcRequestBuilders.post(URL_BASE)
                .content(getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.erros").value("Funcionário não encontrado, id inexistente."))
            .andExpect(jsonPath("$.data").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    fun atualizarLancamento(){
        `when`(lancamentoService?.buscarPorId(LANCAMENTO_ID)).thenReturn(lancamentoDto())

        mvc!!.perform(
            MockMvcRequestBuilders.put(URL_BASE + LANCAMENTO_ID)
                .content(getJsonRequestPut())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)
    }

    @Test
    @Throws(Exception::class)
    fun removerLancamento(){
        `when`(lancamentoService?.buscarPorId(LANCAMENTO_ID)).thenReturn(lancamentoDto())

        mvc!!.perform(
            MockMvcRequestBuilders.delete(URL_BASE + LANCAMENTO_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)
    }

    private fun getJsonRequestPost(): String {
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        return mapper.writeValueAsString(
            LancamentoDto(
                DateUtils.localDateTimeToStringBr(DATA_TIME),
                TipoEnum.INICIO_TRABALHO.name,
                null,
                null,
                FUNCIONARIO_ID
            )
        )
    }

    private fun getJsonRequestPut(): String {
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        return mapper.writeValueAsString(
            LancamentoDto(
                DateUtils.localDateTimeToStringBr(DATA_TIME),
                TipoEnum.INICIO_ALMOCO.name,
                null,
                null,
                FUNCIONARIO_ID
            )
        )
    }

    private fun getFuncionario(): Funcionario =
        Funcionario(
            EMPLOYEE_NAME,
            EMAIL,
            SENHA,
            CPF,
            PerfilEnum.ROLE_ADMIN,
            EMPRESA_ID
        )


    private fun getLancamentoWithOutId(): Lancamento =
        Lancamento(
            data = DATA_TIME,
            tipo = TipoEnum.INICIO_TRABALHO,
            funcionarioId = FUNCIONARIO_ID,
            "Teste",
            "Teste",
            id = null.toString()
        )

    private fun getLancamento(): Lancamento =
        Lancamento(
            data = DATA_TIME,
            tipo = TipoEnum.INICIO_TRABALHO,
            funcionarioId = FUNCIONARIO_ID,
            descricao = "null",
            localizacao = "null",
            id = LANCAMENTO_ID
        )

    private fun lancamentoDto() =
        LancamentoDto(
            data = DateUtils.localDateTimeToStringBr(DATA_TIME),
            tipo = TipoEnum.INICIO_TRABALHO.name,
            descricao = null,
            localizacao = null,
            funcionarioId = FUNCIONARIO_ID
        )
}