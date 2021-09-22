package br.com.zupacademy.propostas.api.cartao.viagem;

import br.com.zupacademy.propostas.api.cartao.client.CartaoClient;
import feign.FeignException;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long idCartao;

    @NotNull
    @NotBlank
    private String destinoViagem;

    @NotNull
    @FutureOrPresent
    private LocalDateTime dataTerminoViagem;

    private final LocalDateTime dataCriacaoAviso = LocalDateTime.now();

    private EnumResultadoAvisoViagem statusSistemaLegado;

    private String ip;

    private String agent;

    @Deprecated
    public Viagem() {
    }

    public Viagem(Long idCartao, String destinoViagem, LocalDateTime dataTerminoViagem, String ip,
                  String agent) {
        this.idCartao = idCartao;
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.ip = ip;
        this.agent = agent;
    }

    public void comunicaSistemaLegado(String numCartao, CartaoClient cartaoClient) {
        ViagemForm viagemForm = new ViagemForm(this.destinoViagem, this.dataTerminoViagem);
        AvisoViagemResponse avisoViagemResponse = null;

        try {
            avisoViagemResponse = cartaoClient.comunicarAvisoDeViagemAoSistemaLegado(numCartao, viagemForm);
        } catch (FeignException feignException) {
            feignException.printStackTrace();
        }

        assert avisoViagemResponse != null;

        if (avisoViagemResponse.getResultadoAvisoViagem() == EnumResultadoAvisoViagem.CRIADO) {
            this.statusSistemaLegado = EnumResultadoAvisoViagem.CRIADO;
        } else {
            this.statusSistemaLegado = EnumResultadoAvisoViagem.FALHA;
        }
    }

    public Long getId() {
        return id;
    }

    public EnumResultadoAvisoViagem getStatusSistemaLegado() { return statusSistemaLegado; }

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
