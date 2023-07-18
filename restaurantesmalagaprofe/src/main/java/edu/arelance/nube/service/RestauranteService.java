package edu.arelance.nube.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.arelance.nube.dto.FraseChuckNorris;
import edu.arelance.nube.repository.entity.Restaurante;

public interface RestauranteService {
	
	Page<Restaurante> consultarPorPagina (Pageable pageable);
	
	Iterable<Restaurante> buscarPorRangoPrecio(int preciomin, int preciomax, Pageable pageable);
	
	Iterable<Restaurante> consultarTodos ();
	
	Optional<Restaurante> consultarRestaurante (Long id);
	
	Restaurante altaRestaurante (Restaurante restaurante);
	
	void borrarRestaurante (Long id);
	
	Optional<Restaurante> modificarRestaurante (Long id, Restaurante restaurante);
	
	Iterable<Restaurante> buscarPorRangoPrecio (int preciomin, int preciomax);

	Iterable<Restaurante> buscarPorBarrioNombreOEspecialidad (String clave);
	
	Iterable<String> obtenerTodosLosBarrios();
	
	Optional<FraseChuckNorris> obtenerFraseAleatorioChuckNorris ();
	
	
	
	
	
	
}
