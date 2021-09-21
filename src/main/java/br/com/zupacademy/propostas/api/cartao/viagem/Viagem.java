package br.com.zupacademy.propostas.api.cartao.viagem;

import br.com.zupacademy.propostas.api.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String numCartao;

    @NotNull
    @NotBlank
    private String destinoViagem;

    @NotNull
    @FutureOrPresent
    private LocalDateTime dataTerminoViagem;

    private final LocalDateTime dataCriacaoAviso = LocalDateTime.now();

    private String ip;

    private String agent;

    @Deprecated
    public Viagem() {
    }

    public Viagem(String numCartao, String destinoViagem, LocalDateTime dataTerminoViagem, String ip,
                  String agent) {
        this.numCartao = numCartao;
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.ip = ip;
        this.agent = agent;
    }

    public Viagem(String destinoViagem, LocalDateTime dataTerminoViagem) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
    }

    public Long getId() {
        return id;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDateTime getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    public LocalDateTime getDataCriacaoAviso() {
        return dataCriacaoAviso;
    }

    public String getIp() {
        return ip;
    }

    public String getAgent() {
        return agent;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }
}
