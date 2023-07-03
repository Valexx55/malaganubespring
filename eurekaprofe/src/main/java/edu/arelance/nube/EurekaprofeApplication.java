package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//activo/pongo el marcha el servidor de Eureka
public class EurekaprofeApplication {
	
	/**
	 * 1 Creo proyecto con Spring Starter Project y add dependecia de EurekaServer
	 * 2 Add depedencia de glasfish JAXB
	 * 3 Anotación en el main de @EnableEurekaServer
	 * 4 Configuro las propeties
	 */

	public static void main(String[] args) {
		SpringApplication.run(EurekaprofeApplication.class, args);
	}

}
