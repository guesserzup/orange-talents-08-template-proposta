package br.com.zupacademy.propostas.api.cartao.bloqueio;

import java.time.LocalDateTime;

public class BloqueioDto {

    private LocalDateTime horaBloqueio;
    private String ip;
    private String agent;
    @Deprecated
    public BloqueioDto() {
    }

    public BloqueioDto(LocalDateTime horaBloqueio, String ip, String userAgent) {
        this.horaBloqueio = horaBloqueio;
        this.ip = ip;
        this.agent = userAgent;
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
}
