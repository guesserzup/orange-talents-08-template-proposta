package br.com.zupacademy.propostas.api.cartao.carteira;

public class CarteiraDto {

    private EnumResultadoCarteira resultado;
    private String idCarteira;

    @Deprecated
    public CarteiraDto() {
    }

    public CarteiraDto(EnumResultadoCarteira resultado, String idCarteira) {
        this.resultado = resultado;
        this.idCarteira = idCarteira;
    }

    public EnumResultadoCarteira getResultado() {
        return resultado;
    }

    public String getIdCarteira() {
        return idCarteira;
    }
}
