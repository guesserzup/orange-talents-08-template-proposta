package br.com.zupacademy.propostas.seguranca;

import org.springframework.stereotype.Component;

@Component
public class MascaraDados {

    public String generico(String str) {
        char[] strChars = str.toCharArray();
        for( int i = 0; i < strChars.length - 4; i++ ) {
            strChars[i] = 'X';
        }
        return new String(strChars);
    }
}
