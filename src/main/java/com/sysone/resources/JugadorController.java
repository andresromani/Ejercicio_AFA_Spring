package com.sysone.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysone.dtos.HistorialJugadorEquipoDto;
import com.sysone.entities.Equipo;
import com.sysone.entities.HistorialJugadorEquipo;
import com.sysone.entities.Jugador;
import com.sysone.service.EquipoServiceImp;
import com.sysone.service.JugadorServiceImp;

@RestController
@RequestMapping("jugadores")
public class JugadorController {

	@Autowired
	private JugadorServiceImp jugadorService;
	
	@Autowired
	private EquipoServiceImp equipoService;
	
	// JUGADORES
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardarJugador(@Valid @RequestBody Jugador jugador, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<String> errores = null;
		if (result.hasErrors()) {
			errores = new ArrayList<String>();
			for(ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}
			
			response.put("errores", errores);
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}
		
		try {
			Jugador j = jugadorService.save(jugador);
			
			if (j != null) {
				response.put("jugador", j);
				response.put("mensaje", "el jugador se ha creado exitosamente");
				responseEntity = ResponseEntity.ok(response);
			} else {
				response.put("mensaje", "el jugador no se ha creado exitosamente");
				responseEntity = ResponseEntity.status(500).body(response);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "el jugador no se ha creado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> listar() {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<Jugador> jugadores = jugadorService.findAll();
		if (jugadores.size() > 0) {
			response.put("cantidad", jugadores.size());
			response.put("jugadores", jugadores);
			responseEntity = ResponseEntity.ok(response);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}
		
		return responseEntity;
	}
	
	@GetMapping("{dni}")
	public ResponseEntity<Jugador> listarPorDni(@PathVariable String dni) {
		Optional<Jugador> optionalJugador = jugadorService.findById(dni);
		if (optionalJugador.isPresent()) {
			return ResponseEntity.ok(optionalJugador.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PatchMapping("{dni}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable String dni, @Valid @RequestBody Jugador jugador, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<String> errores = null;
		if (result.hasErrors()) {
			errores = new ArrayList<String>();
			for(ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}
			
			response.put("errores", errores);
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}
		
		try {
			jugador.setDni(dni);
			Jugador j = jugadorService.save(jugador);
			if (j != null) {
				response.put("jugador", j);
				response.put("mensaje", "el jugador con dni se ha actualizado exitosamente");
				responseEntity = ResponseEntity.ok(response);
			} else {
				response.put("mensaje", "el jugador no se ha actualizado exitosamente");
				responseEntity = ResponseEntity.status(500).body(response);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "el jugador no se ha actualizado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("{dni}")
	public ResponseEntity<Map<String, String>> eliminar(@PathVariable String dni) {
		Map<String, String> response = new HashMap<String, String>();
		ResponseEntity<Map<String, String>> responseEntity = null;
		
		try {
			jugadorService.delete(dni);
			response.put("mensaje", "el jugador se ha eliminado exitosamente");
			responseEntity = ResponseEntity.status(500).body(response);
		} catch(DataAccessException e) {
			response.put("mensaje", "el jugador no se ha eliminado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	// HISTORIALES
	
	@PostMapping("{dni}/historiales")
	public ResponseEntity<Map<String, Object>> guardarHistorial(@PathVariable String dni, @Valid @RequestBody HistorialJugadorEquipo historial, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<String> errores = null;
		if (result.hasErrors()) {
			errores = new ArrayList<>();
			for (ObjectError e : result.getAllErrors()) {
				errores.add(e.getDefaultMessage());
			}
			
			response.put("errores", errores);
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}
		
		try {
			if (historial.getEquipo() != null) {
				Optional<Jugador> optionalJugador = jugadorService.findById(dni);
				Optional<Equipo> optionalEquipo = equipoService.findById(historial.getEquipo().getCuit());
				
				if (optionalJugador.isPresent() && optionalEquipo.isPresent()) {
					historial.setJugador(optionalJugador.get());
					historial.setEquipo(optionalEquipo.get());
					HistorialJugadorEquipo h = jugadorService.saveHistorial(historial);
					
					if (h != null) {
						response.put("historial", h);
						response.put("mensaje", "el historial del jugador con dni " + dni + " se ha creado exitosamente");
						responseEntity = ResponseEntity.ok(response);
					} else {
						response.put("mensaje", "el historial no se ha creado exitosamente");
						responseEntity = ResponseEntity.status(500).body(response);
					}
				} else {
					response.put("mensaje", "el historial no se ha creado exitosamente: el equipo/jugador no existe");
					responseEntity = ResponseEntity.badRequest().body(response);
				}
			} else {
				response.put("mensaje", "el historial no se ha creado exitosamente: el equipo no puede ser null");
				responseEntity = ResponseEntity.badRequest().body(response);
			}
		} catch(DataAccessException e) {
			response.put("mensaje", "el historial no se ha creado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@GetMapping("{dni}/historiales")
	public ResponseEntity<Map<String, Object>> listarHistorialPorJugador(@PathVariable String dni) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<HistorialJugadorEquipoDto> historiales = jugadorService.listarHistoriales(dni);
		if (historiales.size() > 0) {
			response.put("cantidad", historiales.size());
			response.put("historiales", historiales);
			responseEntity = ResponseEntity.ok(response);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}
		
		return responseEntity;
	}
	
	@GetMapping("{dni}/historiales/{id}")
	public ResponseEntity<HistorialJugadorEquipoDto> listarHistorialPorId(@PathVariable String dni, @PathVariable String id) {
		List<HistorialJugadorEquipoDto> historiales = jugadorService.listarHistoriales(dni);
		HistorialJugadorEquipoDto his = null;
		ResponseEntity<HistorialJugadorEquipoDto> responseEntity = null;
		
		if (historiales != null && historiales.size() > 0) {
			for (HistorialJugadorEquipoDto h : historiales) {
				if (h.getId().equals(id)) {
					his = h;
				}
			}
			
			if (his != null) {
				responseEntity = ResponseEntity.ok(his);
			} else {
				responseEntity = ResponseEntity.noContent().build();
			}
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}
		
		return responseEntity;
	}
	
	@PatchMapping("{dni}/historiales/{id}")
	public ResponseEntity<Map<String, Object>> actualizarHistorial(@PathVariable String dni, @PathVariable String id, @Valid @RequestBody HistorialJugadorEquipo historial, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<String> errores = null;
		if (result.hasErrors()) {
			errores = new ArrayList<>();
			for (ObjectError e : result.getAllErrors()) {
				errores.add(e.getDefaultMessage());
			}
			
			response.put("errores", errores);
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}
		
		try {
			if (historial.getEquipo() != null) {
				Optional<Jugador> optionalJugador = jugadorService.findById(dni);
				Optional<Equipo> optionalEquipo = equipoService.findById(historial.getEquipo().getCuit());
				
				if (optionalJugador.isPresent() && optionalEquipo.isPresent()) {
					historial.setId(id);
					historial.setJugador(optionalJugador.get());
					historial.setEquipo(optionalEquipo.get());
					HistorialJugadorEquipo h = jugadorService.saveHistorial(historial);
					
					if (h != null) {
						response.put("historial", h);
						response.put("mensaje", "el historial se ha actualizado exitosamente");
						responseEntity = ResponseEntity.ok(response);
					} else {
						response.put("mensaje", "el historial no se ha actualizado exitosamente");
						responseEntity = ResponseEntity.status(500).body(response);
					}
				} else {
					response.put("mensaje", "el historial no se ha actualizado exitosamente: el equipo/jugador no existe");
					responseEntity = ResponseEntity.badRequest().body(response);
				}
			} else {
				response.put("mensaje", "el campo no se ha actualizado exitosamente: el equipo no puede ser null");
				responseEntity = ResponseEntity.badRequest().body(response);
			}
		} catch(DataAccessException e) {
			response.put("mensaje", "el historial no se ha actualizado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("{dni}/historiales/{id}")
	public ResponseEntity<Map<String, String>> deleteHistorial(@PathVariable String dni, @PathVariable String id) {
		Map<String, String> response = new HashMap<String, String>();
		ResponseEntity<Map<String, String>> responseEntity = null;
		
		try {
			jugadorService.eliminarHistorialPorId(id);
			response.put("mensaje", "el historial del jugador con dni " + dni + " se ha eliminado exitosamente");
			responseEntity = ResponseEntity.ok(response);
		} catch (DataAccessException e) {
			response.put("mensaje", "el historial no se ha eliminado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
}
