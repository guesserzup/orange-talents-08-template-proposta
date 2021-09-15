package br.com.zupacademy.propostas.api.analise.connector;

import br.com.zupacademy.propostas.api.analise.Analise;
import br.com.zupacademy.propostas.api.analise.AnaliseForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "localhost:9999/api/", name = "AnaliseApi")
public interface AnaliseConnector {

    @PostMapping(value = "solicitacao")
    Analise solicitaAnalise(@RequestBody AnaliseForm form);
}
