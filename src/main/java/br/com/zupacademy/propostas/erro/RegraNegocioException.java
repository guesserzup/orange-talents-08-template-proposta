package br.com.zupacademy.propostas.erro;

public class RegraNegocioException extends RuntimeException {
    private final RetornaErro erroRetorno;

    public RegraNegocioException(String message, String field, Object value) {
        erroRetorno = new RetornaErro();
        erroRetorno.addErrorField(message, field, value);
    }

    public RetornaErro getErroRetorno() { return erroRetorno; }
}