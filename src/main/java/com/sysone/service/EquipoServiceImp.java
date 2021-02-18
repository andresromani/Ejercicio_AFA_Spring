package com.sysone.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysone.dtos.EquipoDto;
import com.sysone.dtos.HistorialJugadorEquipoDto;
import com.sysone.dtos.JugadorDto;
import com.sysone.entities.Equipo;
import com.sysone.entities.HistorialJugadorEquipo;
import com.sysone.entities.Jugador;
import com.sysone.entities.Posicion;
import com.sysone.repository.IEquipoRepository;
import com.sysone.repository.IHistorialJugadorEquipoRepository;

@Service
public class EquipoServiceImp implements IEquipoService {

	@Autowired
	private IEquipoRepository iEquipoRep;
	
	@Autowired
	private IHistorialJugadorEquipoRepository iHistorialRep;
	
	@Override
	@Transactional(readOnly = true)
	public List<Equipo> findAll(Sort sort) {
		return iEquipoRep.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Equipo> findById(String cuit) {
		return iEquipoRep.findById(cuit);
	}

	@Override
	@Transactional
	public Equipo save(Equipo equipo) {
		return iEquipoRep.save(equipo);
	}

	@Override
	@Transactional
	public void delete(String cuit) {
		iEquipoRep.deleteById(cuit);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Jugador> listarJugadores(String cuit) {
		List<HistorialJugadorEquipo> historiales = iHistorialRep.listarPorEquipo(cuit);
		List<Jugador> jugadores = new ArrayList<>();
		historiales.forEach(h -> {
			if (h.getFecha_fin() != null && h.getFecha_fin().isAfter(LocalDate.now())) {
				jugadores.add(h.getJugador());
			}
		});
		return jugadores;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Jugador> listarJugadores(String cuit, LocalDate fecha) {
		List<HistorialJugadorEquipo> historiales = iHistorialRep.listarPorEquipo(cuit);
		List<Jugador> jugadores = new ArrayList<>();
		historiales.forEach(h -> {
			if (h.getFecha_fin() != null && (((h.getFecha_inicio().isBefore(fecha) || h.getFecha_inicio().isEqual(fecha)) && h.getFecha_fin().isAfter(fecha)) || h.getFecha_fin().isEqual(fecha))) {
				jugadores.add(h.getJugador());
			}
		});
		return jugadores;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HistorialJugadorEquipoDto> listarHistoriales(String cuit) {
		List<HistorialJugadorEquipoDto> historialesDto = new ArrayList<>();
		
		iHistorialRep.listarPorEquipo(cuit).forEach(h -> {
			HistorialJugadorEquipoDto historialDto = entityToDto(h);
			historialesDto.add(historialDto);
		});
		
		return historialesDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Jugador> listarJugadoresPorPosicion(String cuit, Posicion posicion) {
		List<HistorialJugadorEquipo> historiales = iHistorialRep.listarPorEquipo(cuit);
		List<Jugador> jugadores = new ArrayList<>();
		if (posicion != null) {
			historiales.forEach(h -> {
				if ((h.getFecha_fin() != null && h.getFecha_fin().isAfter(LocalDate.now())) && (h.getPosicion().getNombrePosicion().equals(posicion.getNombrePosicion()))) {
					jugadores.add(h.getJugador());
				}
			});
		}
		return jugadores;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Jugador> listarJugadoresPorPosicion(String cuit, Posicion posicion, LocalDate fecha) {
		List<HistorialJugadorEquipo> historiales = iHistorialRep.listarPorEquipo(cuit);
		List<Jugador> jugadores = new ArrayList<>();
		if (posicion != null) {
			historiales.forEach(h -> {
				if (h.getFecha_fin() != null && ((h.getFecha_inicio().isBefore(fecha) || h.getFecha_inicio().isEqual(fecha)) && ((h.getFecha_fin().isAfter(fecha) || h.getFecha_fin().isEqual(fecha)))) && (h.getPosicion().getNombrePosicion().equals(posicion.getNombrePosicion()))) {
					jugadores.add(h.getJugador());
				}
			});
		}
		return jugadores;
	}

	@Override
	public HistorialJugadorEquipoDto entityToDto(HistorialJugadorEquipo historial) {
		HistorialJugadorEquipoDto historialDto = new HistorialJugadorEquipoDto();
		EquipoDto equipoDto = new EquipoDto();
		JugadorDto jugadorDto = new JugadorDto();
		
		equipoDto.setCuit(historial.getEquipo().getCuit());
		equipoDto.setNombre(historial.getEquipo().getNombre());
		equipoDto.setCategoria(historial.getEquipo().getCategoria());
		
		jugadorDto.setDni(historial.getJugador().getDni());
		jugadorDto.setNombre(historial.getJugador().getNombre());
		jugadorDto.setApellido(historial.getJugador().getApellido());
		
		historialDto.setId(historial.getId());
		historialDto.setEquipo(equipoDto);
		historialDto.setJugador(jugadorDto);
		historialDto.setFecha_inicio(historial.getFecha_inicio());
		historialDto.setFecha_fin(historial.getFecha_fin());
		historialDto.setPosicion(historial.getPosicion());
		
		return historialDto;
	}

}
