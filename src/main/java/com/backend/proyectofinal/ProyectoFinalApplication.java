package com.backend.proyectofinal;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoFinalApplication {

	public static void main(String[] args) {

		PropertyConfigurator.configure("log4j.properties");
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}

}
