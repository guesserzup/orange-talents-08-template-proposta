package br.com.zupacademy.propostas.proposta;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DocumentoEncrypt {

    private final String document;

    public DocumentoEncrypt(String document) {
        this.document = document;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(document);
    }
}
