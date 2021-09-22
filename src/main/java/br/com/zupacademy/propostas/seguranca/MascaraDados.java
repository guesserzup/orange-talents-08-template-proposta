package br.com.zupacademy.propostas.seguranca;

import org.springframework.stereotype.Component;

@Component
public class MascaraDados {

    public static String generico(String str) {

        if (str == null) {
            return "";
        }

        char[] strChars = str.toCharArray();
        for( int i = 0; i < strChars.length - 4; i++ ) {
            strChars[i] = 'X';
        }
        return new String(strChars);
    }
}
