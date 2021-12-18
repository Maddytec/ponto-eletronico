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
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
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
    private val modelMapper: ModelMapper? = null

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    companion object {
        private val URL_BASE = "/api/lancamento"
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
    fun CadastrarLancamentoTest(){

        val lancamentoWithOutId = getLancamentoWithOutId()
        `when`(funcionarioService?.buscarPorId(FUNCIONARIO_ID)).thenReturn(getFuncionario())
        `when`(lancamentoService?.salvar(lancamentoDto())).thenReturn(lancamentoDto())

        mvc!!.perform(MockMvcRequestBuilders.post(URL_BASE)
            .content(getJsonRequestPost())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tipo").value(TIPO_INICIO_TRABALHO))
           // .andExpect(jsonPath("$.data").value(DATA_TIME.format(DateUtils.formmaterDateHourBr)))
           // .andExpect(jsonPath("$.funcionarioId").value(FUNCIONARIO_ID))
            .andExpect(jsonPath("$.data.erros").isEmpty)
    }

    private fun getJsonRequestPost(): String {
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        return mapper.writeValueAsString(LancamentoDto(
            DateUtils.localDateTimeToStringBr(DATA_TIME),
            TipoEnum.INICIO_TRABALHO.name,
            null,
            null,
            FUNCIONARIO_ID
        ))
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
            tipo =TipoEnum.INICIO_TRABALHO,
            funcionarioId = FUNCIONARIO_ID,
            "Teste",
            "Teste",
            id = null.toString()
        )

    private fun getLancamento(): Lancamento =
        Lancamento(
            DATA_TIME,
            TipoEnum.INICIO_TRABALHO,
            FUNCIONARIO_ID,
            "null",
            "null",
            LANCAMENTO_ID
        )

    private fun lancamentoDto() =
        LancamentoDto(
            DATA_TIME.toString(),
            TipoEnum.INICIO_TRABALHO.name,
            null,
            null,
            FUNCIONARIO_ID
        )
}