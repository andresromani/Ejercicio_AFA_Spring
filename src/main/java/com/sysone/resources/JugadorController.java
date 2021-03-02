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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		
		Optional<Jugador> jugadorOptional = jugador.getDni() != null ? jugadorService.findById(jugador.getDni()) : Optional.empty();
		if (jugadorOptional.isPresent()) {
			response.put("mensaje", "el jugador no se ha creado exitosamente: el jugador con dni " + jugador.getDni() + " ya existe");
			return ResponseEntity.status(409).body(response);
		}
		
		List<String> errores = null;
		if (result.hasErrors()) {
			errores = new ArrayList<String>();
			for(ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}
			
			response.put("errores", errores);
			return ResponseEntity.badRequest().body(response);
		}
		
		try {
			Jugador j = jugadorService.save(jugador);
			
			response.put("jugador", j);
			response.put("mensaje", "el jugador se ha creado exitosamente");
			responseEntity = ResponseEntity.ok(response);
		} catch (DataAccessException e) {
			response.put("mensaje", "el jugador no se ha creado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> listar() {
		Map<String, Object> response = new HashMap<String, Object>();
		
		List<Jugador> jugadores = jugadorService.findAll();
		if (jugadores.size() > 0) {
			response.put("cantidad", jugadores.size());
			response.put("jugadores", jugadores);
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{dni}")
	public ResponseEntity<Jugador> listarPorDni(@PathVariable String dni) {
		Optional<Jugador> optionalJugador = jugadorService.findById(dni);
		if (optionalJugador.isPresent()) {
			return ResponseEntity.ok(optionalJugador.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{dni}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable String dni, @Valid @RequestBody Jugador jugador, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		Optional<Jugador> jugadorOptional = jugadorService.findById(dni);
		if (jugadorOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
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
			
			response.put("jugador", j);
			response.put("mensaje", "el jugador se ha actualizado exitosamente");
			responseEntity = ResponseEntity.ok(response);
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
		
		Optional<Jugador> jugadorOptional = jugadorService.findById(dni);
		if (jugadorOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			jugadorService.delete(dni);
			response.put("mensaje", "el jugador se ha eliminado exitosamente");
			responseEntity = ResponseEntity.ok(response);
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
		
		Optional<Jugador> optionalJugador = jugadorService.findById(dni);
		Optional<Equipo> optionalEquipo = historial.getEquipo() != null ? equipoService.findById(historial.getEquipo().getCuit()) : Optional.empty();
		
		if (optionalJugador.isEmpty() || optionalEquipo.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
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
			historial.setJugador(optionalJugador.get());
			historial.setEquipo(optionalEquipo.get());
			HistorialJugadorEquipoDto h = jugadorService.entityToDto(jugadorService.saveHistorial(historial));
			
			response.put("historial", h);
			response.put("mensaje", "el historial del jugador con dni " + dni + " se ha creado exitosamente");
			responseEntity = ResponseEntity.ok(response);
		} catch(DataAccessException e) {
			response.put("mensaje", "el historial no se ha creado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@GetMapping("{dni}/historiales")
	public ResponseEntity<Map<String, Object>> listarHistorialPorJugador(@PathVariable String dni) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		List<HistorialJugadorEquipoDto> historiales = jugadorService.listarHistoriales(dni);
		if (historiales.size() > 0) {
			response.put("cantidad", historiales.size());
			response.put("historiales", historiales);
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{dni}/historiales/{id}")
	public ResponseEntity<HistorialJugadorEquipoDto> listarHistorialPorId(@PathVariable String dni, @PathVariable String id) {
		Optional<Jugador> optionalJugador = jugadorService.findById(dni);
		HistorialJugadorEquipoDto historial = jugadorService.listarHistorialPorId(id);
		if (optionalJugador.isEmpty() || historial == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(historial);
	}
	
	@PutMapping("{dni}/historiales/{id}")
	public ResponseEntity<Map<String, Object>> actualizarHistorial(@PathVariable String dni, @PathVariable String id, @Valid @RequestBody HistorialJugadorEquipo historial, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		HistorialJugadorEquipoDto optionalHistorial = jugadorService.listarHistorialPorId(id);
		Optional<Jugador> optionalJugador = jugadorService.findById(dni);
		Optional<Equipo> optionalEquipo = historial.getEquipo() != null ? equipoService.findById(historial.getEquipo().getCuit()) : Optional.empty();
		
		if (optionalHistorial == null || optionalJugador.isEmpty() || optionalEquipo.isEmpty()) {
			responseEntity = ResponseEntity.notFound().build();
			return responseEntity;
		}
		
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
			historial.setId(id);
			historial.setJugador(optionalJugador.get());
			historial.setEquipo(optionalEquipo.get());
			HistorialJugadorEquipoDto h = jugadorService.entityToDto(jugadorService.saveHistorial(historial));
			
			response.put("historial", h);
			response.put("mensaje", "el historial del jugador con dni " + dni + " se ha actualizado exitosamente");
			responseEntity = ResponseEntity.ok(response);
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
		
		HistorialJugadorEquipoDto historial = jugadorService.listarHistorialPorId(id);
		Optional<Jugador> jugadorOptional = jugadorService.findById(dni);
		if (historial == null || jugadorOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
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
