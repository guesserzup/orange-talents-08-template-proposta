package br.com.zupacademy.propostas.api.cartao.bloqueio;

import java.time.LocalDateTime;

public class BloqueioDto {

    private LocalDateTime horaBloqueio;
    private String ip;
    private String agent;
    private String cartao;

    @Deprecated
    public BloqueioDto() {
    }

    public BloqueioDto(LocalDateTime horaBloqueio, String ip, String userAgent, String cartao) {
        this.horaBloqueio = horaBloqueio;
        this.ip = ip;
        this.agent = userAgent;
        this.cartao = cartao;
    }

    public LocalDateTime getHoraBloqueio() {
        return horaBloqueio;
    }

    public String getIp() {
        return ip;
    }

    public String getAgent() {
        return agent;
    }

    public String getCartao() {
        return cartao;
    }
}
