package br.com.zupacademy.propostas.erro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetornaErro {

    private List<String> global = new ArrayList<>();
    private List<HashMap> fields = new ArrayList<>();

    public void AddError (String errorMessage){
        global.add(errorMessage);
    }

    public void addErrorField (String message, String field, Object value){

        HashMap<String,Object> map = new HashMap<>();
        map.put("Field",field);
        map.put("Value",value);
        map.put("Message",message);
        map.put("Time", LocalDateTime.now());

        fields.add(map);
    }

    public List<?> getGlobal() {
        return global;
    }

    public List<?> getFields() {
        return fields;
    }
}
