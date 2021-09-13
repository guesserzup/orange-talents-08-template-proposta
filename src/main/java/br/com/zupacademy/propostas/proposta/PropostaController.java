package br.com.zupacademy.propostas.proposta;

import br.com.zupacademy.propostas.api.Analise;
import br.com.zupacademy.propostas.api.AnaliseForm;
import br.com.zupacademy.propostas.api.AnaliseRepository;
import br.com.zupacademy.propostas.api.connector.FeignConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseRepository analiseRepository;

    @Autowired
    private FeignConnector feignConnector;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaForm form, UriComponentsBuilder uri) {
        Proposta proposta = form.toModel();

        Analise analiseCliente = feignConnector.solicitaAnalise(new AnaliseForm(proposta.getDocumento(), proposta.getNome(), proposta.getId()));

        if (Objects.equals(analiseCliente.getResultadoSolicitacao(), "COM_RESTRICAO")) {
            proposta.setEstadoProposta("NAO_ELEGIVEL");
        } else {
            proposta.setEstadoProposta("ELEGIVEL");
        }
        
        analiseRepository.save(analiseCliente);
        proposta = propostaRepository.save(proposta);

        URI location = uri.path("/propostas/{id}").build(proposta.getId());
        return ResponseEntity.created(location).body(new PropostaDto(proposta.getDocumento(), proposta.getEmail(),
                proposta.getNome(), proposta.getEndereco(), proposta.getSalario()));
    }
}
