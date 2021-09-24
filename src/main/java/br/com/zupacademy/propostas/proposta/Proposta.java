package br.com.zupacademy.propostas.proposta;

import br.com.zupacademy.propostas.api.analise.Analise;
import br.com.zupacademy.propostas.api.analise.AnaliseForm;
import br.com.zupacademy.propostas.api.analise.EnumResultadoAnalise;
import br.com.zupacademy.propostas.api.analise.client.AnaliseClient;
import br.com.zupacademy.propostas.api.cartao.Cartao;
import br.com.zupacademy.propostas.validacao.CpfCnpj;;
import feign.FeignException;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @CpfCnpj
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotNull
    private EnumEstadoProposta estadoProposta = EnumEstadoProposta.NAO_ELEGIVEL;

    private String numCartao;

    @OneToOne
    private Cartao cartao;

    public Proposta(DocumentoEncrypt documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento.hash();
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    @Deprecated
    public Proposta() {
    }

    public void analisaProposta(AnaliseClient analiseClient) throws FeignException {
        AnaliseForm analiseForm = new AnaliseForm(this.getDocumento(), this.getNome(), this.getId());
        Analise analise = null;

        try {
            analise = analiseClient.solicitaAnalise(analiseForm);
        } catch (FeignException feignException) {
            feignException.printStackTrace();
        }

        assert analise != null;

        this.estadoProposta = analise.getResultadoSolicitacao() == EnumResultadoAnalise.COM_RESTRICAO ?
                EnumEstadoProposta.NAO_ELEGIVEL : EnumEstadoProposta.ELEGIVEL;
    }

    public void associaCartao(Cartao cartao) {
        this.numCartao = cartao.getNumCartao();
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
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

    public String getNumCartao() { return numCartao; }

    public Cartao getCartao() { return cartao; }
}
