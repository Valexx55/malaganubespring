package edu.arelance.nube.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =  WebEnvironment.RANDOM_PORT)
public class RestauranteControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	TestRestTemplate template;
	
	
	@Test
	public void testGetRestaurantes () {
		assertThat(this.template.getForObject("http://localhost:"+ port+"/restaurante", String.class)).contains("barrio");
	}
	
	
	@BeforeEach
	public void antesDeCadaTest ()
	{
		System.out.println("antes de cada métodos test");
	}
	
	@BeforeAll
	public static void antesDeTodosLosTests ()
	{
		System.out.println("antes de todos los métodos test");
	}
	
	@AfterEach
	public void despuesDeCadaTest ()
	{
		System.out.println("despues de cada métodos test");
	}
	
	@AfterAll
	public static void despuesDeTodosLosTests ()
	{
		System.out.println("despues de cada todos métodos test");
	}

}
