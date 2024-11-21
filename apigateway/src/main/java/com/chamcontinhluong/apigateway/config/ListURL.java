package com.chamcontinhluong.apigateway.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ListURL {
    Map<String,Map<String,String>> listUrl = new HashMap<>();
    public ListURL(){
        this.listUrl.put("/api/v1/employee",Map.of("GET","view_employee"));
        this.listUrl.put("/api/v1/employee/{idEmployee}",Map.of("GET","view_personal_employee"));
        this.listUrl.put("/api/v1/employee/departments",Map.of("GET","view_departments_employee"));
    }
    public Boolean isPermissionsUrl(List<String> permissions,String url,String method){
        String perm = listUrl.get(url).get(method);
        if(perm != null){
            return permissions.contains(perm);
        }
        return false;
    }

}