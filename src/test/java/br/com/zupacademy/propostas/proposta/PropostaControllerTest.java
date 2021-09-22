package br.com.zupacademy.propostas.proposta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class PropostaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PropostaRepository propostaRepository;

    @Test
    void deveCadastrarNovaProposta() throws Exception {

    }
}