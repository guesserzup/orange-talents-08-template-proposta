package br.com.zupacademy.propostas.biometria;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BiometriaForm {

    @NotNull
    @NotBlank
    @JsonProperty(value = "fingerprint")
    private String fingerprint;

    public String getFingerprint() { return fingerprint; }

    public void setFingerprint(String fingerprint) { this.fingerprint = fingerprint; }

    public Biometria toModel(Cartao cartao) { return new Biometria(fingerprint, cartao); }
}
