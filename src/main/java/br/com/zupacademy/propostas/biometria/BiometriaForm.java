package br.com.zupacademy.propostas.biometria;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Base64;

public class BiometriaForm {

    @NotNull
    @NotBlank
    @JsonProperty(value = "fingerprint")
    private String fingerprint;

    public String getFingerprint() { return fingerprint; }

    public void setFingerprint(String fingerprint) { this.fingerprint = fingerprint; }

    public Biometria toModel(Cartao cartao) { return new Biometria(fingerprint, cartao); }

    public void validacaoBiometria(String fingerprint) {
        try {
            byte[] fingerprintByte = Base64.getDecoder().decode(fingerprint);
            String teste = Base64.getEncoder().encodeToString(fingerprintByte);
            Assert.isTrue(teste.equals(this.fingerprint), "Base64 Inválida");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fingerprint cadastrado é inválido!");
        }
    }
}
