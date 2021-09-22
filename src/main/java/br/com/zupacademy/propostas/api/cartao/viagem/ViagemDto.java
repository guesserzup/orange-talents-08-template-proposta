package br.com.zupacademy.propostas.api.cartao.viagem;

import java.time.LocalDateTime;

public class ViagemDto {

    private Long idCartao;
    private String destinoViagem;
    private LocalDateTime dataTerminoViagem;
    private final LocalDateTime dataCriacaoAviso = LocalDateTime.now();
    private String ip;
    private String agent;

    @Deprecated
    public ViagemDto() {
    }

    public ViagemDto(Long numCartao, String destinoViagem, LocalDateTime dataTerminoViagem, String ip, String agent) {
        this.idCartao = numCartao;
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.ip = ip;
        this.agent = agent;
    }

    public Long getIdCartao() {
        return idCartao;
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
}
