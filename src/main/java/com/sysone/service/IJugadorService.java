package com.sysone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sysone.dtos.HistorialJugadorEquipoDto;
import com.sysone.entities.HistorialJugadorEquipo;
import com.sysone.entities.Jugador;

@Service
public interface IJugadorService {

	public List<Jugador> findAll();
	public Optional<Jugador> findById(String dni);
	public Jugador save(Jugador jugador);
	public void delete(String dni);
	public HistorialJugadorEquipo saveHistorial(HistorialJugadorEquipo historial);
	public List<HistorialJugadorEquipoDto> listarHistoriales(String dni);
	public void eliminarHistorialPorId(String id);
	public HistorialJugadorEquipoDto entityToDto(HistorialJugadorEquipo historial);
	
}
