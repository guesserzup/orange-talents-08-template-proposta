package br.com.zupacademy.propostas.api.cartao.carteira;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraForm {

    @NotBlank
    @NotNull
    private String email;
    
    @NotBlank
    @NotNull
    private EnumCarteira carteira;

    @Deprecated
    public CarteiraForm() {
    }

    public CarteiraForm(String email, EnumCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public EnumCarteira getCarteira() {
        return carteira;
    }
}
