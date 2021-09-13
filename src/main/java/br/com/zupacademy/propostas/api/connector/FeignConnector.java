package br.com.zupacademy.propostas.api.connector;

import br.com.zupacademy.propostas.api.Analise;
import br.com.zupacademy.propostas.api.AnaliseForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "localhost:9999/api", name = "api")
public interface FeignConnector {

    @PostMapping(value = "/solicitacao")
    Analise solicitaAnalise(@RequestBody AnaliseForm form);
}
