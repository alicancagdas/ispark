package com.ispark.package_creator_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  // Bu anotasyonu ekleyin

public class PackageCreatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackageCreatorServiceApplication.class, args);
	}

}
