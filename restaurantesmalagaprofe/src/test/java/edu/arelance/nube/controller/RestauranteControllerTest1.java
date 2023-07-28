package edu.arelance.nube.controller;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.MediaType;

import edu.arelance.nube.repository.entity.Restaurante;

@SpringBootTest
@AutoConfigureMockMvc // mockeo el servidor@SpringBootTest
public class RestauranteControllerTest1 {

	/**
	 * La idea es testear una api REST de forma «interna» sin tener que desplegar la
	 * aplicación en un servidor, pero también de forma realista efectuando las
	 * llamadas a la misma tal y como lo harían los consumidores del servicio.
	 */

	@Autowired
	private MockMvc mockMvc; // el objeto con el que lanzamos las peticiones HTTP

	@Autowired
	ObjectMapper om;

	@Test
	public void insertarAlumnoTest() throws Exception {
		
		ObjectNode on = om.createObjectNode();

		// PASARLO A JSON
		on.put("nombre", "Donde SARA");
		on.put("direccion", "C/ Seta marina");
		on.put("barrio", "La Jarta");
		on.put("web", "www.martinete.org");
		on.put("fichaGoogle", "http://gogle.xe");
		on.put("latitud", 40.32);
		on.put("longuitud", 3.68);
		on.put("precio", 30);
		on.put("especialidad1", "carne");
		on.put("especialidad2", "arroz");
		on.put("especialidad3", "pescado");
		on.put("creadoEn", LocalDateTime.now().toString());

		String json_restaurante = on.toString();// serializo - lo paso a JSON

		mockMvc.perform(post("/restaurante")
				.contentType(MediaType.APPLICATION_JSON).
				content(json_restaurante))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json"));

	}

}
