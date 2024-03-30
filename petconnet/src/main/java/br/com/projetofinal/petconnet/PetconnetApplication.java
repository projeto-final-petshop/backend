package br.com.projetofinal.petconnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PetconnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetconnetApplication.class, args);
	}

}
