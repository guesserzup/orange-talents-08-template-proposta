package br.com.zupacademy.propostas.api.cartao.viagem;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.CartaoRepository;
import br.com.zupacademy.propostas.seguranca.MascaraDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ViagemController {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping("/cartao/{idCartao}/viagem")
    public ViagemDto cadastraAviso(@PathVariable("idCartao") Long idCartao, @RequestBody ViagemForm form,
                                   HttpServletRequest request, @AuthenticationPrincipal Jwt tokenJwt) {

        Cartao cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existente na base de dados."));

        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");

        Viagem viagem = new Viagem(cartao.getIdCartao(), form.getDestinoViagem(), form.getDataTerminoViagem(), ip, agent);

        viagemRepository.save(viagem);

        return new ViagemDto(viagem.getIdCartao(), MascaraDados.generico(viagem.getDestinoViagem()), viagem.getDataTerminoViagem(),
                MascaraDados.generico(viagem.getIp()), viagem.getAgent());
    }
}
