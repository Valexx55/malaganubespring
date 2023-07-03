package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayprofeApplication {
	
	/**
	 * Para hacer el GAteway
	 * 
	 * 1) Nuevo proyecto con Spring Starter Proyect con las 
	 * depdencias de Gateway Y Eureka Client
	 * 
	 * 2) Anotación con @EnableEurekaClient en el main
	 * 3) Porperties /  yml configuración /  enrutamiento
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(GatewayprofeApplication.class, args);
	}

}
