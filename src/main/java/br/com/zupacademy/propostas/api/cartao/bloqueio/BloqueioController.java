package br.com.zupacademy.propostas.api.cartao.bloqueio;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.CartaoRepository;
import br.com.zupacademy.propostas.erro.RegraNegocioException;
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

    @Transactional
    @PostMapping("/cartao/{numCartao}/bloqueio")
    public BloqueioDto bloquear(@PathVariable("numCartao") String numCartao, HttpServletRequest request,
                                @AuthenticationPrincipal Jwt tokenJwt) {

        Cartao cartao = cartaoRepository.findById(numCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existente na base de dados."));

        if (!cartao.getProposta().getDocumento().equals(tokenJwt.getClaims().get("documento"))) {
            throw new RegraNegocioException("Cartão informado não está associado com o usuário.", "cartao", numCartao);
        }

        if (cartao.isBloqueado()) {
            throw new RegraNegocioException("Cartão informado já está bloqueado no sistema.", "cartao", numCartao);
        }

        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");

        cartao.setBloqueado(true);
        Bloqueio bloqueio = new Bloqueio(ip, agent, cartao);

        cartaoRepository.save(cartao);
        bloqueioRepository.save(bloqueio);

        return new BloqueioDto(bloqueio.getHoraBloqueio(), bloqueio.getIp(), bloqueio.getAgent(),
                bloqueio.getAgent());
    }
}
