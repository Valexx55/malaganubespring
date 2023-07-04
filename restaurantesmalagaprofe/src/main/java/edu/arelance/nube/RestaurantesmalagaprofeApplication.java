package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient//activamos el cliente eureka
public class RestaurantesmalagaprofeApplication {
	
	/**
	 * PARA CONFIGURAR EL CLIENTE EUREKA
	 * 
	 * 1) Add starters, eureka client
	 * 2) Add Anotación @EnableEurekaClient en el main
	 * 3) configuramos properties relativas a eureka 
	 * 
	 *
	 */

	public static void main(String[] args) {
		SpringApplication.run(RestaurantesmalagaprofeApplication.class, args);
	}

}
