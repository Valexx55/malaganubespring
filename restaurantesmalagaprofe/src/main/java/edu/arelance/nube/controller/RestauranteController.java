package edu.arelance.nube.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

import edu.arelance.nube.dto.FraseChuckNorris;
import edu.arelance.nube.repository.entity.Restaurante;
import edu.arelance.nube.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * API WEB
 * HTTP -> Deriva en la ejecución de un método
 * 
 * GET -> Consultar TODOS
 * GET -> Consultar Uno (por ID)
 * POST -> Insertar un restaurante nuevo
 * PUT -> Modificar un restaurante que ya existe
 * DELETE -> Borrar un resutaurante (por ID)
 * 
 * GET -> Búsqueda -> Por barrio, por especialidad, por nombre, etc..
 */


//@Controller//Devolvemos una vista(html/jsp)
@RestController//Devolvemos JSON http://localhost:8081/restaurante
@RequestMapping("/restaurante")
public class RestauranteController {
	
	@Autowired
	RestauranteService restauranteService;
	
	@Autowired
	Environment environment; //de aquí voy a sacar la info del puerto
		
	Logger logger = LoggerFactory.getLogger(RestauranteController.class);
	
	
	@GetMapping("/test") // GET http://localhost:8081/restaurante/test
	public Restaurante obtenerRestauranteTest ()
	{
		Restaurante restaurante = null;
		
			System.out.println("Llamando a obtenerRestauranteTest");
			logger.debug("estoy en obtenerRestauranteTest");
			restaurante = new Restaurante(1l, "Martinete", "Carlos Haya 33", "Carranque", "www.martinete.org", "http://gogle.xe", 33.65f, -2.3f, 10, "gazpachuelo", "paella", "sopa de marisco", LocalDateTime.now());
		
		return restaurante;
	}
	
	 //* GET -> Consultar TODOS http://localhost:8081/restaurante
	@GetMapping
	public ResponseEntity<?> listarTodos ()
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> lista_Restaurantes = null;
		
			//String saludo = "HOLA";
			//saludo.charAt(10);
			logger.debug("ATENDIDO POR EL PUERTO " + environment.getProperty("local.server.port"));
			lista_Restaurantes = this.restauranteService.consultarTodos();
			responseEntity = ResponseEntity.ok(lista_Restaurantes);
		
		return responseEntity;
	}
	
	private ResponseEntity<?> generarRespuestaErroresValdicacion 
	(BindingResult bindingResult)
	{
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> listaErrores = null;
		
			listaErrores = bindingResult.getAllErrors();
			//imprimir los errores por el log
			listaErrores.forEach(e -> logger.error(e.toString()));
			
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		
		return responseEntity;
		
	}
	//http://localhost:8081/restaurante/12
	 //* GET -> Consultar Uno (por ID) http://localhost:8081/restaurante/5
	@Operation(description = "Este servicio consulta restaurantes por un id")
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId (@PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> or = null;
		
			logger.debug("En listarPorId " +id);
			or = this.restauranteService.consultarRestaurante(id);
			if (or.isPresent())
			{ //la consulta ha recuperado un registro
				
				Restaurante restauranteLeido =  or.get();
				responseEntity = ResponseEntity.ok(restauranteLeido);
				logger.debug("Recuperado el registro " + restauranteLeido);
				
			}else {
				////la consulta NO ha recuperado un registro
				responseEntity = ResponseEntity.noContent().build();
				logger.debug("El restaurante con " + id + " no existe");
			}
			logger.debug("Saliendo de listarPorId ");
		
		return responseEntity;
	}
	
	
	//* POST -> Insertar un restaurante nuevo http://localhost:8081/restaurante (Body Restaurante)
	@PostMapping
	public ResponseEntity<?> insertarRestaurante (@Valid @RequestBody Restaurante restaurante, BindingResult bindingResult)
	{
		ResponseEntity<?> responseEntity = null;
		Restaurante restauranteNuevo = null;
		
			//TODO validar
			if (bindingResult.hasErrors()) {
				logger.debug("Errores en la entrada POST");
				responseEntity = generarRespuestaErroresValdicacion(bindingResult);
				
			}else {
				logger.debug("SIN Errores en la entrada POST");
				restauranteNuevo = this.restauranteService.altaRestaurante(restaurante);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restauranteNuevo);
			
				
			}
			
		return responseEntity;
	}
	
	@PostMapping("/crear-con-foto") //POST localhost:8081/restaurante/crear-con-foto
	public ResponseEntity<?> insertarRestauranteConFoto (@Valid Restaurante restaurante, BindingResult bindingResult, MultipartFile archivo) throws IOException
	{
		ResponseEntity<?> responseEntity = null;
		Restaurante restauranteNuevo = null;
		
			//TODO validar
			if (bindingResult.hasErrors()) {
				logger.debug("Errores en la entrada POST");
				responseEntity = generarRespuestaErroresValdicacion(bindingResult);
				
			}else {
				logger.debug("SIN Errores en la entrada POST");
				
				if (!archivo.isEmpty())
				{
					logger.debug("El restaurante trae foto");
					try {
						restaurante.setFoto(archivo.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Error al tratar la foto", e);
						throw e;
					}
				}
				
				restauranteNuevo = this.restauranteService.altaRestaurante(restaurante);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restauranteNuevo);
			
				
			}
			
		return responseEntity;
	}
	
	
	@GetMapping("/obtenerFoto/{id}") //GET localhost:8081/restaurante/obtenerFoto/8
	public ResponseEntity<?> obtenerFotoRestaurante (@PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> or = null;
		Resource imagen = null;//esto representa el archivo
		
			logger.debug("En obtenerFotoRestaurante " +id);
			or = this.restauranteService.consultarRestaurante(id);
			if (or.isPresent()&&or.get().getFoto()!=null)
			{ //la consulta ha recuperado un registro
				
				Restaurante restauranteLeido =  or.get();
				imagen = new ByteArrayResource(restauranteLeido.getFoto());
				responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
				logger.debug("Recuperado foto del registro " + restauranteLeido);
				
			}else {
				////la consulta NO ha recuperado un registro
				responseEntity = ResponseEntity.noContent().build();
				logger.debug("El restaurante con " + id + " no existe o no tiene foto");
			}
			logger.debug("Saliendo de obtenerFotoRestaurante ");
		
		return responseEntity;
	}
	
	
	
	
	//* PUT -> Modificar un restaurante que ya existe http://localhost:8081/restaurante/id (Body Restaurante)
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarRestaurante (@Valid @RequestBody Restaurante restaurante, BindingResult bindingResult, @PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> opRest = null;
			
			 if (bindingResult.hasErrors())
			 {
				 logger.debug("ERRORES en PUT");
				 responseEntity = generarRespuestaErroresValdicacion(bindingResult);
					
			 } else {
				 logger.debug("SIN ERRORES en PUT");
				 opRest = this.restauranteService.modificarRestaurante(id, restaurante);
				 if (opRest.isPresent())
				 {
					 Restaurante rm =  opRest.get();
					 responseEntity = ResponseEntity.ok(rm);
				 } else {
					 responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				 }
				 
			 }
			 
		return responseEntity;
	} 
	
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> modificarRestauranteConFoto (@Valid Restaurante restaurante, BindingResult bindingResult, MultipartFile archivo,@PathVariable Long id) throws IOException
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> opRest = null;
			
			 if (bindingResult.hasErrors())
			 {
				 logger.debug("ERRORES en PUT");
				 responseEntity = generarRespuestaErroresValdicacion(bindingResult);
					
			 } else {
				 logger.debug("SIN ERRORES en PUT");
				 
				 if (!archivo.isEmpty())
				 {
					 try {
						restaurante.setFoto(archivo.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Error al tratar la foto ", e);
						throw e;
					}
				 }
				 
				 opRest = this.restauranteService.modificarRestaurante(id, restaurante);
				 if (opRest.isPresent())
				 {
					 Restaurante rm =  opRest.get();
					 responseEntity = ResponseEntity.ok(rm);
				 } else {
					 responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				 }
				 
			 }
			 
		return responseEntity;
	} 
	
	
	//* DELETE -> Borrar un resutaurante (por ID) http://localhost:8081/restaurante/3
	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrarPorId (@PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		
			this.restauranteService.borrarRestaurante(id);
			responseEntity = ResponseEntity.ok().build();
		
		return responseEntity;
	}

	//GET http://localhost:8081/restaurante/buscarPorPrecio?preciomin=10&preciomax=40
	@GetMapping("/buscarPorPrecio")
	public ResponseEntity<?> listarPorRangoPrecio (
			@RequestParam(name = "preciomin") int preciomin,
			@RequestParam(name = "preciomax") int preciomax)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> lista_Restaurantes = null;
			
			lista_Restaurantes = this.restauranteService.buscarPorRangoPrecio(preciomin, preciomax);
			responseEntity = ResponseEntity.ok(lista_Restaurantes);
		
		return responseEntity;
	}
	
	//GET http://localhost:8081/restaurante/buscarPorPrecioPaginado?preciomin=10&preciomax=40&page=0&size=3
	@GetMapping("/buscarPorPrecioPaginado")
	public ResponseEntity<?> listarPorRangoPrecioPaginado (
			@RequestParam(name = "preciomin") int preciomin,
			@RequestParam(name = "preciomax") int preciomax,
			Pageable pageable)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> lista_Restaurantes = null;
			
			lista_Restaurantes = this.restauranteService.buscarPorRangoPrecio(preciomin, preciomax, pageable);
			responseEntity = ResponseEntity.ok(lista_Restaurantes);
		
		return responseEntity;
	}
	
	////GET http://localhost:8081/restaurante/buscarPorBarrioNombreOEspecialidad?clave=papa
	@GetMapping("/buscarPorBarrioNombreOEspecialidad")
	public ResponseEntity<?> buscarPorBarrioNombreOEspecialidad (@RequestParam(name = "clave") String clave)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> lista_Restaurantes = null;
			
			lista_Restaurantes = this.restauranteService.buscarPorBarrioNombreOEspecialidad(clave);
			responseEntity = ResponseEntity.ok(lista_Restaurantes);
		
		return responseEntity;
	}
	
	// Consultar todos lo barrios. Metodo GET a http://localhost:8081/restaurante/barrios
    @GetMapping("/barrios")
    public ResponseEntity<?> obtenerListadoBarrios() {
        ResponseEntity<?> responseEntity = null;
        Iterable<String> lista_barrios = null;

	        lista_barrios = this.restauranteService.obtenerTodosLosBarrios();
	        responseEntity = ResponseEntity.ok(lista_barrios);

        return responseEntity;
    }
    
    /**
     * Obtener una frase aleatoria de Chuck Norris. 
     * Metodo GET a http://localhost:8081/restaurante/fraseChuckNorris
     * @return
     */
    @GetMapping("/fraseChuckNorris")
	public ResponseEntity<?> obtieneFrase(){
		ResponseEntity<?> responseEntity = null; 
		Optional<FraseChuckNorris> opFrase = null;
		
			opFrase = this.restauranteService.obtenerFraseAleatorioChuckNorris();
			if (opFrase.isPresent())
			{
				FraseChuckNorris frase = opFrase.get();
				responseEntity = ResponseEntity.ok(frase);
			} else {
				responseEntity = ResponseEntity.noContent().build();
			}
			
			
		return responseEntity;
		
	}
	
    
    @GetMapping("/pagina") //GET http://localhost:8081/restaurante/pagina?page=0&size=2
	public ResponseEntity<?> obtenerResturantesPorPagina (Pageable pageable)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> pagina_Restaurantes = null;
		
			pagina_Restaurantes = this.restauranteService.consultarPorPagina(pageable);
			responseEntity = ResponseEntity.ok(pagina_Restaurantes);
		
		return responseEntity;
	}
    
    
}







