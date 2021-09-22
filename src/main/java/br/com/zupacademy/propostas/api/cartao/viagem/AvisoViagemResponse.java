package br.com.zupacademy.propostas.api.cartao.viagem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvisoViagemResponse {

    @JsonProperty(value = "resultado")
    private EnumResultadoAvisoViagem resultadoAvisoViagem;

    @Deprecated
    public AvisoViagemResponse() {
    }

    public AvisoViagemResponse(EnumResultadoAvisoViagem resultadoAvisoViagem) {
        this.resultadoAvisoViagem = resultadoAvisoViagem;
    }

    public EnumResultadoAvisoViagem getResultadoAvisoViagem() {
        return resultadoAvisoViagem;
    }
}
