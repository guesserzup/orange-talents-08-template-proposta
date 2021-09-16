package br.com.zupacademy.propostas.api.cartao;

import br.com.zupacademy.propostas.biometria.Biometria;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @NotNull
    @Column(unique = true)
    @JsonProperty(value = "id")
    private String numCartao;

    private LocalDateTime emitidoEm;

    private String titular;

    @JsonProperty(value = "idProposta")
    public String idProposta;

    private BigDecimal limite;

    @JsonProperty(value = "vencimento.dia")
    private Integer diaVencimento;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometria;

    public String getNumeroCartao() {
        return numCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public List<Biometria> getBiometria() { return biometria; }

    public void setBiometria(Biometria biometria) { this.biometria.add(biometria); }

    @Override
    public String toString() {
        return "Cartao{" + "numCartao='" + numCartao + '\'' + ", emitidoEm=" + emitidoEm + ", titular='" + titular + '\''
                + ", idProposta='" + idProposta + '\'' + ", limite=" + limite + ", diaVencimento=" + diaVencimento
                + ", biometria=" + biometria + '}';
    }
}
