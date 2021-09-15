package br.com.zupacademy.propostas.proposta;

import br.com.zupacademy.propostas.api.analise.Analise;
import br.com.zupacademy.propostas.api.analise.AnaliseForm;
import br.com.zupacademy.propostas.api.analise.AnaliseRepository;
import br.com.zupacademy.propostas.api.analise.connector.AnaliseConnector;
import br.com.zupacademy.propostas.api.cartao.AssociaCartao;
import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.CartaoRepository;
import br.com.zupacademy.propostas.api.cartao.connector.CartaoConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseRepository analiseRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AnaliseConnector feignConnector;

    @Autowired
    private CartaoConnector cartaoConnector;

    @Autowired
    private AssociaCartao associaCartao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaForm form, UriComponentsBuilder uri) {
        Proposta proposta = form.toModel();

        AnaliseForm analiseForm = new AnaliseForm(proposta.getDocumento(), proposta.getNome(), proposta.getId());
        Analise analiseCliente = feignConnector.solicitaAnalise(analiseForm);

        if (Objects.equals(analiseCliente.getResultadoSolicitacao(), "SEM_RESTRICAO")) {
            proposta.setEstadoProposta("ELEGIVEL");
        } else {
            proposta.setEstadoProposta("NAO_ELEGIVEL");
        }

        analiseRepository.save(analiseCliente);
        proposta = propostaRepository.save(proposta);

        URI location = uri.path("/propostas/{id}").build(proposta.getId());
        return ResponseEntity.created(location).body(new PropostaDto(proposta.getDocumento(), proposta.getEmail(),
                proposta.getNome(), proposta.getEndereco(), proposta.getSalario()));
    }
}
