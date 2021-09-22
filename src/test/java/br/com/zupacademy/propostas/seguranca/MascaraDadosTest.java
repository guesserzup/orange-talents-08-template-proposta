package br.com.zupacademy.propostas.seguranca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MascaraDadosTest {
    
    @Autowired
    MascaraDados mascaraDados;
    
    @Test
    public void testSolution() {
        assertEquals("XXXXXXXXXXXX5616", mascaraDados.generico("4556364607935616"));
        assertEquals("XXXXXXX5616",      mascaraDados.generico(     "64607935616"));
        assertEquals("1",                mascaraDados.generico(               "1"));
        assertEquals("",                 mascaraDados.generico(                ""));
    }
}