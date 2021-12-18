package br.com.maddytec.pontoeletronico.services

import br.com.maddytec.pontoeletronico.documents.Funcionario
import br.com.maddytec.pontoeletronico.enums.PerfilEnum
import br.com.maddytec.pontoeletronico.repository.FuncionarioRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class FuncionarioServiceTest {

    @Autowired
    private val funcionarioService: FuncionarioService? = null

    @MockBean
    private val funcionariolRepository: FuncionarioRepository? = null

    private val EMAIL = "maddytec@gmail.com"
    private val CPF = "12345678910"
    private val ID = "1"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp(){
        BDDMockito.given(funcionariolRepository?.save(Mockito.any(Funcionario::class.java))).willReturn(funcionario())
        BDDMockito.given(funcionariolRepository?.findById(ID)).willReturn(Optional.of(funcionario()))
        BDDMockito.given(funcionariolRepository?.findByEmail(EMAIL)).willReturn(funcionario())
        BDDMockito.given(funcionariolRepository?.findByCpf(CPF)).willReturn(funcionario())
    }

    @Test
    fun salvarTest(){
        val funcionario: Funcionario? = funcionarioService?.salvar(funcionario())
        Assertions.assertNotNull(funcionario)
    }

    @Test
    fun buscarPorIdTest(){
        val funcionario: Funcionario? = funcionarioService?.buscarPorId(ID)
        Assertions.assertNotNull(funcionario)
    }

    @Test
    fun buscarPorEmailTest(){
        val funcionario: Funcionario? = funcionarioService?.buscarPorEmail(EMAIL)
        Assertions.assertNotNull(funcionario)
    }

    @Test
    fun buscarPorCpfTest(){
        val funcionario: Funcionario? = funcionarioService?.buscarPorCpf(CPF)
        Assertions.assertNotNull(funcionario)
    }

    private fun funcionario(): Funcionario = Funcionario(
        "Madson Silva",
        EMAIL,
        "123456789",
         CPF,
        PerfilEnum.ROLE_ADMIN,
        "1",
        null,
        null,
        null
    )


}