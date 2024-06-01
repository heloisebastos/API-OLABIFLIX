package com.olabi.olabiflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OlabiflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlabiflixApplication.class, args);
	}

}
