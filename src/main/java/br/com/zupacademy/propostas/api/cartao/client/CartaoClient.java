package br.com.zupacademy.propostas.api.cartao.client;

import br.com.zupacademy.propostas.api.analise.AnaliseForm;
import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.bloqueio.BloqueioForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${feign.CartaoApi.url}", name = "CartaoApi")
public interface CartaoClient {

    @PostMapping(value = "cartoes")
    Cartao associarCartao(@RequestBody AnaliseForm analiseForm);

    @PostMapping(value = "cartoes/{idCartao}/bloqueios")
    void bloquearCartaoSistemaLegado(@PathVariable("idCartao") String numCartao, @RequestBody BloqueioForm form);
}
