package com.sysone.resources;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sysone.dtos.HistorialJugadorEquipoDto;
import com.sysone.entities.Equipo;
import com.sysone.entities.Jugador;
import com.sysone.entities.Posicion;
import com.sysone.service.EquipoServiceImp;

@RestController
@RequestMapping("equipos")
public class EquipoController {
	
	@Autowired
	private EquipoServiceImp equipoService;
	
	// EQUIPOS
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardarEquipo(@Valid @RequestBody Equipo equipo, BindingResult result) {
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
			Equipo e = equipoService.save(equipo);
			
			if (e != null) {
				response.put("equipo", e);
				response.put("mensaje", "el equipo se ha creado exitosamente");
				responseEntity = ResponseEntity.ok(response);
			} else {
				response.put("mensaje", "el equipo no se ha creado exitosamente");
				responseEntity = ResponseEntity.status(500).body(response);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "el equipo no se ha creado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> listar() {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		Sort sortPorNombre = Sort.by("nombre");
		List<Equipo> equipos = equipoService.findAll(sortPorNombre);
		if (equipos.size() > 0) {
			response.put("cantidad", equipos.size());
			response.put("equipos", equipos);
			responseEntity = ResponseEntity.ok(response);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}
		
		return responseEntity;
	}
	
	@GetMapping("{cuit}")
	public ResponseEntity<Equipo> listarPorCuit(@PathVariable String cuit) {
		Optional<Equipo> optionalEquipo = equipoService.findById(cuit);
		if (optionalEquipo.isPresent()) {
			return ResponseEntity.ok(optionalEquipo.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PatchMapping("{cuit}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable String cuit, @Valid @RequestBody Equipo equipo, BindingResult result) {
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
			equipo.setCuit(cuit);
			Equipo e = equipoService.save(equipo);
			
			if (e != null) {
				response.put("equipo", e);
				response.put("mensaje", "el equipo se ha actualizado exitosamente");
				responseEntity = ResponseEntity.ok(response);
			} else {
				response.put("mensaje", "el equipo no se ha actualizado exitosamente");
				responseEntity = ResponseEntity.status(500).body(response);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "el equipo no se ha actualizado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("{cuit}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String cuit) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		try {
			equipoService.delete(cuit);
			response.put("mensaje", "el equipo se ha eliminado exitosamente");
			responseEntity = ResponseEntity.ok(response);
		} catch (DataAccessException e) {
			response.put("mensaje", "el equipo no se ha eliminado exitosamente: " + e.getMostSpecificCause().toString());
			responseEntity = ResponseEntity.status(500).body(response);
		}
		
		return responseEntity;
	}
	
	// JUGADORES POR EQUIPO
	
	@GetMapping("{cuit}/jugadores")
	public ResponseEntity<Map<String, Object>> listarJugadores(@PathVariable String cuit, @RequestParam(required = false) String posicion, @RequestParam(required = false) String fecha) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<Jugador> jugadores = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		if (posicion != null) {
			Posicion pos = null;
			pos = posicion.equalsIgnoreCase("de") ? Posicion.DELANTERO : pos;
			pos = posicion.equalsIgnoreCase("df") ? Posicion.DEFENSOR : pos;
			pos = posicion.equalsIgnoreCase("md") ? Posicion.MEDIOCAMPISTA : pos;
			pos = posicion.equalsIgnoreCase("ar") ? Posicion.ARQUERO : pos;
			if (fecha != null) {
				LocalDate fechaFormateada = LocalDate.parse(fecha, formatter);
				jugadores = equipoService.listarJugadoresPorPosicion(cuit, pos, fechaFormateada);
			} else {
				jugadores = equipoService.listarJugadoresPorPosicion(cuit, pos);
			}
		} else if (fecha != null) {
			LocalDate fechaFormateada = LocalDate.parse(fecha, formatter);
			jugadores = equipoService.listarJugadores(cuit, fechaFormateada);
		} else {
			jugadores = equipoService.listarJugadores(cuit);
		}
		
		if (jugadores.size() > 0) {
			response.put("cantidad", jugadores.size());
			response.put("jugadores", jugadores);
			responseEntity = ResponseEntity.ok(response);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}
		
		return responseEntity;
	}
	
	// HISTORIALES DE UN EQUIPO
	
	@GetMapping("{cuit}/historiales")
	public ResponseEntity<Map<String, Object>> listarHistoriales(@PathVariable String cuit) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<HistorialJugadorEquipoDto> historiales = equipoService.listarHistoriales(cuit);
		if (historiales.size() > 0) {
			response.put("cantidad", historiales.size());
			response.put("historiales", historiales);
			responseEntity = ResponseEntity.ok(response);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}
		
		return responseEntity;
	}
	
}
