package com.chamcongtinhluong.employee.communicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "http://localhost:8082/api/v1")
public interface AccountServiceClient {

    @PostMapping("/create_account")
    String createAccount(@RequestBody CreateAccountRequest createAccountRequest);
    @PutMapping("/account/changestatus/{idemployee}")
    String changeStatus(@PathVariable String idemployee);
    @DeleteMapping("/account/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable String username);

}
