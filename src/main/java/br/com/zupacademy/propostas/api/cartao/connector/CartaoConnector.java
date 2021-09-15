package br.com.zupacademy.propostas.api.cartao.connector;

import br.com.zupacademy.propostas.api.analise.AnaliseForm;
import br.com.zupacademy.propostas.api.cartao.Cartao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@FeignClient(url = "localhost:8888/api/", name = "CartaoApi")
public interface CartaoConnector {

    @PostMapping(value = "cartoes")
    Cartao associarCartao(HashMap<String,String> AnaliseForm);
}
