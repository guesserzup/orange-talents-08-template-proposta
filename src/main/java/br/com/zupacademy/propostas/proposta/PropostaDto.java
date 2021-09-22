package br.com.zupacademy.propostas.proposta;

import java.math.BigDecimal;

public class PropostaDto {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private EnumEstadoProposta estadoProposta;
    private String numeroCartao;

    public PropostaDto(String documento, String email, String nome, String endereco, BigDecimal salario,
                       EnumEstadoProposta estadoProposta, String numCartao) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.estadoProposta = estadoProposta;
        this.numeroCartao = numCartao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EnumEstadoProposta getEstadoProposta() { return estadoProposta; }

    public String getNumeroCartao() { return numeroCartao; }
}
