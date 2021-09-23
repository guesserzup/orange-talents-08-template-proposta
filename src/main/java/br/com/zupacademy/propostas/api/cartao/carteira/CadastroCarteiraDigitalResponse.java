package br.com.zupacademy.propostas.api.cartao.carteira;

public class CadastroCarteiraDigitalResponse {

    private EnumResultadoCarteira resultado;
    private String id;

    @Deprecated
    public CadastroCarteiraDigitalResponse() {
    }

    public CadastroCarteiraDigitalResponse(EnumResultadoCarteira resultado, String idCarteira) {
        this.resultado = resultado;
        this.id = idCarteira;
    }

    public EnumResultadoCarteira getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CadastroCarteiraDigitalResponse{" + "resultado=" + resultado + ", idCarteira='" + id + '\'' + '}';
    }
}
