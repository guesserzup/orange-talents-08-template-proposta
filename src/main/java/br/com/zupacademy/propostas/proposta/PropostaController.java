package br.com.zupacademy.propostas.proposta;

import br.com.zupacademy.propostas.api.analise.client.AnaliseClient;
import br.com.zupacademy.propostas.seguranca.MascaraDados;
import feign.FeignException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseClient analiseClient;

    @Autowired
    private Tracer tracer;

    @Autowired
    private MeterRegistry registry;

    @GetMapping("/{idProposta}")
    public PropostaDto busca(@PathVariable("idProposta") Long idProposta) {
        
        Proposta proposta = propostaRepository
                .findById(idProposta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta informada não existe"));

        return new PropostaDto(proposta.getDocumento(), MascaraDados.generico(proposta.getEmail()), MascaraDados.generico(proposta.getNome()),
                MascaraDados.generico(proposta.getEndereco()), proposta.getSalario(), proposta.getEstadoProposta(), MascaraDados.generico(proposta.getNumCartao()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaForm form, UriComponentsBuilder uri) throws FeignException {
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", form.getEmail());

        Counter customCounter = Counter.builder("propostas_requests").register(registry);

        Proposta proposta = form.toModel();
        proposta.analisaProposta(analiseClient);

        proposta = propostaRepository.save(proposta);

        URI location = uri.path("/propostas/{id}").build(proposta.getId());

        customCounter.increment();

        return ResponseEntity.created(location).body(new PropostaDto(proposta.getDocumento(), MascaraDados.generico(proposta.getEmail()),
                MascaraDados.generico(proposta.getNome()), MascaraDados.generico(proposta.getEndereco()), proposta.getSalario(), proposta.getEstadoProposta(), proposta.getNumCartao()));
    }
}
