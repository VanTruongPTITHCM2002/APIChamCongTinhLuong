package com.chamcontinhluong.apigateway.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ListURL {
    Map<String,String> listUrl = new HashMap<String,String>();
    public ListURL(){
        this.listUrl.put("/api/v1/account","view_account");
        this.listUrl.put("/api/v1/account/{username}","update_account");
        this.listUrl.put("/api/v1/account/{username}/reset_password","reset_password");
        this.listUrl.put("/api/v1/account/changestatus/{idemployee}","change_status_account");
        this.listUrl.put("/api/v1/create_account","create_account");
    }
    public Boolean isPermissionsUrl(List<String> permissions,String url){
        String perm = listUrl.get(url);
        if(perm != null){
            return permissions.contains(perm);
        }
        return false;
    }

}