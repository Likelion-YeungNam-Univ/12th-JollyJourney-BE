package com.ll.JollyJourney;

import com.ll.JollyJourney.domain.admin.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.ll.JollyJourney")
@EnableScheduling
public class JollyJourneyApplication {
	@Autowired
	private AdminService adminService;

	public static void main(String[] args) {
		SpringApplication.run(JollyJourneyApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			adminService.createAdminAccount();
		};
	}
}
