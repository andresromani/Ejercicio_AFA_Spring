package com.sysone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysone.dtos.EquipoDto;
import com.sysone.dtos.HistorialJugadorEquipoDto;
import com.sysone.dtos.JugadorDto;
import com.sysone.entities.HistorialJugadorEquipo;
import com.sysone.entities.Jugador;
import com.sysone.repository.IHistorialJugadorEquipoRepository;
import com.sysone.repository.IJugadorRepository;

@Service
public class JugadorServiceImp implements IJugadorService {

	@Autowired
	private IJugadorRepository iJugadorRep;
	
	@Autowired
	private IHistorialJugadorEquipoRepository iHistorialRep;
	
	@Override
	@Transactional(readOnly = true)
	public List<Jugador> findAll() {
		return iJugadorRep.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Jugador> findById(String dni) {
		return iJugadorRep.findById(dni);
	}

	@Override
	@Transactional
	public Jugador save(Jugador jugador) {
		return iJugadorRep.save(jugador);
	}

	@Override
	@Transactional
	public void delete(String dni) {
		iJugadorRep.deleteById(dni);
	}

	@Override
	@Transactional
	public HistorialJugadorEquipo saveHistorial(HistorialJugadorEquipo historial) {
		return iHistorialRep.save(historial);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HistorialJugadorEquipoDto> listarHistoriales(String dni) {
		List<HistorialJugadorEquipoDto> historialesDto = new ArrayList<>();
		
		iHistorialRep.listarPorJugador(dni).forEach(h -> {
			HistorialJugadorEquipoDto historialDto = entityToDto(h);
			historialesDto.add(historialDto);
		});
		
		return historialesDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public HistorialJugadorEquipoDto listarHistorialPorId(String id) {
		Optional<HistorialJugadorEquipo> historialOptional = iHistorialRep.findById(id);
		
		if (historialOptional.isPresent()) {
			HistorialJugadorEquipoDto historialDto = entityToDto(historialOptional.get());
			return historialDto;
		}
		
		return null;
	}
	

	@Override
	@Transactional
	public void eliminarHistorialPorId(String id) {
		iHistorialRep.deleteById(id);
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
