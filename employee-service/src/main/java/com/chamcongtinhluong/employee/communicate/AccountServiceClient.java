package com.chamcongtinhluong.employee.communicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", url = "http://localhost:8082/api/v1")
public interface AccountServiceClient {

    @PostMapping("/create_account")
    String createAccount(@RequestBody CreateAccountRequest createAccountRequest);
    @PutMapping("/account/changestatus/{idemployee}")
    String changeStatus(@PathVariable String idemployee);
}
