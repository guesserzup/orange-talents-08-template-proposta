package br.com.zupacademy.propostas.api.analise.client;

import br.com.zupacademy.propostas.api.analise.Analise;
import br.com.zupacademy.propostas.api.analise.AnaliseForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${feign.AnaliseApi.url}", name = "AnaliseApi")
public interface AnaliseClient {

    @PostMapping(value = "solicitacao")
    Analise solicitaAnalise(@RequestBody AnaliseForm form);
}
