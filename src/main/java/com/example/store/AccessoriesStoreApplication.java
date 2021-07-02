package com.example.store;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.store.config.StorageProperties;
import com.example.store.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AccessoriesStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessoriesStoreApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}

}
