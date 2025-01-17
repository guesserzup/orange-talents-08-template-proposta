package br.com.zupacademy.propostas.api.analise;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Analise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProposta;

    private String documento;

    private String nome;

    private EnumResultadoAnalise resultadoSolicitacao;

    @Deprecated
    public Analise() {
    }

    public Analise(Long idProposta, String documento, String nome, EnumResultadoAnalise resultadoSolicitacao) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public EnumResultadoAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
