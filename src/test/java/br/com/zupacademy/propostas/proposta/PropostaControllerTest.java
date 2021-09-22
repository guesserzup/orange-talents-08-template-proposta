package br.com.zupacademy.propostas.proposta;

import br.com.zupacademy.propostas.api.analise.Analise;
import br.com.zupacademy.propostas.api.analise.EnumResultadoAnalise;
import br.com.zupacademy.propostas.api.analise.client.AnaliseClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class PropostaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AnaliseClient analiseClient;

    @Autowired
    PropostaRepository propostaRepository;

    @Test
    void deveCadastrarNovaProposta() throws Exception {
        PropostaForm form = new PropostaForm("01005446067",
                "jdoe@zup.com.br","John Doe","John's Doe St.", new BigDecimal(2301));

        Analise analiseFinanceira = new Analise(1L, "01005446067", "John Doe", EnumResultadoAnalise.SEM_RESTRICAO);
        Mockito.when(analiseClient.solicitaAnalise(Mockito.any())).thenReturn(analiseFinanceira);

        mvc.perform(MockMvcRequestBuilders.post("/propostas")
                        .content(new ObjectMapper().writeValueAsString(form))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/propostas/*"));

        Assertions.assertEquals(propostaRepository.findByDocumento("01005446067").getEstadoProposta(),
                EnumEstadoProposta.ELEGIVEL);
    }

    @Test
    void naoDeveCadastrarPropostaComDocumentoRepetido() throws Exception {
        PropostaForm formJane = new PropostaForm("01005446067",
                "janedoe@zup.com.br","Jane Doe","Jane's Doe St.", new BigDecimal(2501));

        Analise analiseFinanceiraJane = new Analise(2L, "01005446067", "Jane Doe", EnumResultadoAnalise.SEM_RESTRICAO);
        Mockito.when(analiseClient.solicitaAnalise(Mockito.any())).thenReturn(analiseFinanceiraJane);

        mvc.perform(MockMvcRequestBuilders.post("/propostas")
                        .content(new ObjectMapper().writeValueAsString(formJane))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Assertions.assertEquals(propostaRepository.findAllByDocumento("01005446067").size(), 1);
    }
}