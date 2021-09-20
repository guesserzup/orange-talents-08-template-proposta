package br.com.zupacademy.propostas.api.cartao.bloqueio;

import br.com.zupacademy.propostas.api.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final LocalDateTime horaBloqueio = LocalDateTime.now();
    private String ip;
    private String agent;

    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String ip, String userAgent, Cartao cartao) {
        this.ip = ip;
        this.agent = userAgent;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
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

    public Cartao getCartao() {
        return cartao;
    }
}
