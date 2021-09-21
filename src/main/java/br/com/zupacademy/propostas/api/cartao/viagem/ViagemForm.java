package br.com.zupacademy.propostas.api.cartao.viagem;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ViagemForm {

    @NotNull
    @NotBlank
    private String destinoViagem;

    @NotNull
    @FutureOrPresent
    private LocalDateTime dataTerminoViagem;

    @Deprecated
    public ViagemForm() {
    }

    public ViagemForm(String destinoViagem, LocalDateTime dataTerminoViagem) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDateTime getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    public Viagem toModel() { return new Viagem(this.destinoViagem, this.dataTerminoViagem); }

}
