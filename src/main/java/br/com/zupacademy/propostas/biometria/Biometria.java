package br.com.zupacademy.propostas.biometria;

import br.com.zupacademy.propostas.api.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;

    @NotNull
    @NotBlank
    private String fingerprint;

    private final LocalDateTime dataCadastro = LocalDateTime.now();

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
