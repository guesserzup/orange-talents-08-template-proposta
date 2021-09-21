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

    @Autowired
    private MascaraDados mascaraDados;

    @PostMapping("/cartao/{numCartao}/viagem")
    public ViagemDto cadastraAviso(@PathVariable("numCartao") String numCartao, @RequestBody ViagemForm form,
                                   HttpServletRequest request, @AuthenticationPrincipal Jwt tokenJwt) {

        Cartao cartao =
                cartaoRepository.findById(numCartao).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existente na base de dados."));

        Viagem viagem = form.toModel();

        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");

        viagem.setIp(ip);
        viagem.setAgent(agent);
        viagem.setNumCartao(numCartao);

        viagemRepository.save(viagem);

        return new ViagemDto(mascaraDados.generico(viagem.getNumCartao()),
                mascaraDados.generico(viagem.getDestinoViagem()), viagem.getDataTerminoViagem(),
                mascaraDados.generico(viagem.getIp()), viagem.getAgent());
    }
}
