package br.com.zupacademy.propostas.api.cartao.carteira;

import br.com.zupacademy.propostas.api.cartao.AssociaCartao;
import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.CartaoRepository;
import br.com.zupacademy.propostas.api.cartao.client.CartaoClient;
import br.com.zupacademy.propostas.erro.RegraNegocioException;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class CarteiraController {

    private static final Logger LOGGER = LogManager.getLogger(AssociaCartao.class);

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CartaoClient cartaoClient;

    @PostMapping(value = "/cartao/{idCartao}/carteiras")
    public ResponseEntity<?> associaCarteiraDigital(@PathVariable("idCartao") Long idCartao, @RequestBody CarteiraForm form, UriComponentsBuilder uri) {

        Cartao cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existente na base de dados."));

        CadastroCarteiraDigitalResponse responseAssociacaoLegado = null;

        if (cartao.getCarteiras().toString().contains(form.getCarteira().toString())) {
            LOGGER.warn("Tentativa de associar cartão {} com carteira {} já associada", cartao.getIdCartao(), form.getCarteira());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão já está com esta carteira associada.");
        }

        try {
            responseAssociacaoLegado = cartaoClient.associaCarteiraDigitalSistemaLegado(cartao.getNumCartao(), form);
            LOGGER.info("Carteira {} associada com sucesso para o cartao {}!", form.getCarteira(), cartao.getIdCartao());
        } catch (FeignException exception) {
            LOGGER.error("Falha ao associar carteira digital no sistema legado: {}", exception.getMessage());
        }

        assert responseAssociacaoLegado != null;

        Carteira carteira = new Carteira(form.getCarteira(), responseAssociacaoLegado.getId(), cartao);

        carteiraRepository.save(carteira);
        cartaoRepository.save(cartao);

        URI location = uri.path("/cartao/{idCartao}/carteiras").build(carteira.getId());
        return ResponseEntity.created(location).body(new CarteiraDto(responseAssociacaoLegado.getResultado(), responseAssociacaoLegado.getId()));
    }
}
