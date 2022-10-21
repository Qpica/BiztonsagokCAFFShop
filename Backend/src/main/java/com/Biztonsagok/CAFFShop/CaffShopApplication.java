package com.Biztonsagok.CAFFShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CaffShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffShopApplication.class, args);
	}

}
