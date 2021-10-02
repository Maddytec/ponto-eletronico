package br.com.maddytec.pontoeletronico.services

import br.com.maddytec.pontoeletronico.documents.Empresa
import br.com.maddytec.pontoeletronico.repository.EmpresaRepository
import org.junit.jupiter.api.*
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class EmpresaServiceTest {

    @Autowired
    private val empresaService: EmpresaService? = null

    @MockBean
    private val empresaRepository: EmpresaRepository? = null

    private val CNPJ = "10200049000120"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp(){
        BDDMockito.given(empresaRepository?.save(empresa())).willReturn(empresa())
        BDDMockito.given(empresaRepository?.findByCnpj(CNPJ)).willReturn(empresa())
    }

    @Test
    fun buscarPorCnpjTest(){
        val empresa : Empresa? = empresaService?.buscarPorCnpj(CNPJ)
        Assertions.assertNotNull(empresa)
    }

    @Test
    fun savarTest(){
        val empresa: Empresa? = empresaService?.salvar(empresa())
        Assertions.assertNotNull(empresa)
    }

    private fun empresa() = Empresa("1","MaddyTec", CNPJ)

}