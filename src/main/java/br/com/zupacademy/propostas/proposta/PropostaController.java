package br.com.zupacademy.propostas.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaForm form, UriComponentsBuilder uri) {
        Proposta proposta = form.toModel();
        proposta = propostaRepository.save(proposta);

        URI location = uri.path("/propostas/{id}").build(proposta.getId());
        return ResponseEntity.created(location).body(new PropostaDto(proposta.getDocumento(), proposta.getEmail(),
                proposta.getNome(), proposta.getEndereco(), proposta.getSalario()));
    }
}
