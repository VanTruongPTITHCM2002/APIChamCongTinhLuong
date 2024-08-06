package com.chamcongtinhluong.contract_service.config;


import com.chamcongtinhluong.contract_service.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupTaskExecutor {

    @Autowired
    private ContractService contractService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        // Thực hiện các công việc cần thiết sau khi ứng dụng khởi động
        contractService.updateContractStatus();
    }
}

