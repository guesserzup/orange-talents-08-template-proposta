package br.com.zupacademy.propostas.biometria;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.api.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class BiometriaController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/cartao/{id}/biometria")
    public ResponseEntity<BiometriaDto> cadastrar(@PathVariable("id") String numCartao, @RequestBody @Valid BiometriaForm form, UriComponentsBuilder uri) {

        Cartao cartao = cartaoRepository
                .findById(numCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existente na base!"));

        Biometria biometria = form.toModel(cartao);

        form.validacaoBiometria(biometria.getFingerprint());

        cartao.setBiometria(biometria);
        biometriaRepository.save(biometria);
        cartaoRepository.save(cartao);

        return ResponseEntity.created(uri.path("/cartao/{id}/biometria/{biometria}")
                        .build(cartao.getNumeroCartao(), biometria.getUuid()))
                        .body(new BiometriaDto(biometria.getUuid()));
    }
}
