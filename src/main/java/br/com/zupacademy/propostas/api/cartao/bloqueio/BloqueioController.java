package br.com.zupacademy.propostas.api.cartao.bloqueio;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.CartaoRepository;
import br.com.zupacademy.propostas.api.cartao.client.CartaoClient;
import br.com.zupacademy.propostas.erro.RegraNegocioException;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class BloqueioController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private CartaoClient cartaoClient;

    private static final Logger LOGGER = LogManager.getLogger(BloqueioController.class);

    @Transactional
    @PostMapping("/cartao/{idCartao}/bloqueio")
    public BloqueioDto bloquear(@PathVariable("idCartao") Long idCartao, HttpServletRequest request,
                                @AuthenticationPrincipal Jwt tokenJwt) {

        Cartao cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existente na base de dados."));

        if (!cartao.getProposta().getDocumento().equals(tokenJwt.getClaims().get("documento"))) {
            LOGGER.warn("Tentativa de alterar cartão não associado ao usuário.");
            throw new RegraNegocioException("Cartão informado não está associado com o usuário.", "cartao", idCartao);
        }

        if (cartao.isBloqueado()) {
            LOGGER.warn("Tentativa de bloquear cartão já bloqueado.");
            throw new RegraNegocioException("Cartão informado já está bloqueado no sistema.", "cartao", idCartao);
        }

        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");

        try {
            cartaoClient.bloquearCartaoSistemaLegado(cartao.getNumCartao(), new BloqueioForm(cartao.getNumCartao()));
        } catch (FeignException e) {
            LOGGER.error("Erro processando request do Feign Client", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        Bloqueio bloqueio = new Bloqueio(ip, agent, cartao);

        bloqueioRepository.save(bloqueio);

        return new BloqueioDto(bloqueio.getHoraBloqueio(), bloqueio.getIp(), bloqueio.getAgent());
    }
}
