package edu.arelance.nube.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.arelance.nube.service.RestauranteService;
import edu.arelance.nube.repository.entity.Restaurante;


@WebMvcTest(RestauranteController.class)//levantar el contexto parcialmente de las clases indicadas
public class RestauranteControllerTest2 {
	
	@Autowired
	private MockMvc mockMvc; // el objeto con el que lanzamos las peticiones HTTP
	
	@MockBean
	RestauranteService restauranteService; //es un servicio de mentirijilla--> todo: programar su comportamiento


	
	@Test
	public void getServicioMockeado() throws Exception {
		
		Restaurante restaurante = new Restaurante(1l, "Martinete", "Carlos Haya 33", "Carranque", "www.martinete.org",
				"http://gogle.xe", 33.65f, -2.3f, 10, "gazpachuelo", "paella", "sopa de marisco", null);

		
		
		//programamos el funcionamiento del mock
		when(restauranteService.consultarRestaurante(1l)).thenReturn(Optional.of(restaurante));

		this.mockMvc.perform(get("/restaurante/1")).
		andDo(print()).
		andExpect(status().isOk()).
		andExpect(content().contentType("application/json")).
		andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Martinete")).
		andExpect(MockMvcResultMatchers.jsonPath("$.barrio").value("Carranque"));
	}
}
