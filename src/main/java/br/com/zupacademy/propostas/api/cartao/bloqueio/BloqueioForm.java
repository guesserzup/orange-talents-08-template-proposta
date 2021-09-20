package br.com.zupacademy.propostas.api.cartao.bloqueio;

import javax.validation.constraints.NotBlank;

public class BloqueioForm {

    @NotBlank
    private String numCartao;

    @Deprecated
    public BloqueioForm() {
    }

    public BloqueioForm(String nome) {
        this.numCartao = nome;
    }

    public String getNumCartao() {
        return numCartao;
    }
}
