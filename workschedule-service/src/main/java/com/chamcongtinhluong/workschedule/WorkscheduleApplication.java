package com.chamcongtinhluong.workschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WorkscheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkscheduleApplication.class, args);
	}

}
