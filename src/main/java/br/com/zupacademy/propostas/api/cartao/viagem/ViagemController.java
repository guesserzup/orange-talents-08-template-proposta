package br.com.zupacademy.propostas.api.cartao.viagem;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.CartaoRepository;
import br.com.zupacademy.propostas.api.cartao.client.CartaoClient;
import br.com.zupacademy.propostas.seguranca.MascaraDados;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class ViagemController {

    private static final Logger LOGGER = LogManager.getLogger(ViagemController.class);

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoClient cartaoClient;

    @Transactional
    @PostMapping("/cartao/{idCartao}/viagem")
    public ViagemDto cadastraAviso(@PathVariable("idCartao") Long idCartao, @RequestBody @Valid ViagemForm form,
                                   HttpServletRequest request, @AuthenticationPrincipal Jwt tokenJwt) {

        Cartao cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existente na base de dados."));

        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");

        Viagem viagem = new Viagem(cartao.getIdCartao(), form.getDestinoViagem(), form.getDataTerminoViagem(), ip, agent);

        try {
            viagem.comunicaSistemaLegado(cartao.getNumCartao(), cartaoClient);

            if (viagem.getStatusSistemaLegado() == EnumResultadoAvisoViagem.CRIADO) {
                viagemRepository.save(viagem);
            }

            LOGGER.info("Aviso de viagem comunicado com sucesso!");
        } catch (FeignException exception) {
            LOGGER.error("Erro ao comunicar aviso de viagem ao sistema legado: {}", exception.getMessage());
        }

        return new ViagemDto(viagem.getIdCartao(), MascaraDados.generico(viagem.getDestinoViagem()), viagem.getDataTerminoViagem(),
                MascaraDados.generico(viagem.getIp()), viagem.getAgent());
    }
}
