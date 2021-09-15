package br.com.zupacademy.propostas.api.analise;

import br.com.zupacademy.propostas.validacao.CpfCnpj;

import javax.validation.constraints.NotBlank;

public class AnaliseForm {

    @CpfCnpj
    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private Long idProposta;

    public AnaliseForm(String documento, String nome, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return "AnaliseForm{" + "documento='" + documento + '\'' + ", nome='" + nome + '\'' + ", idProposta='" + idProposta + '\'' + '}';
    }
}
