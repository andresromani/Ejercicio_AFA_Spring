package com.sysone.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sysone.dtos.HistorialJugadorEquipoDto;
import com.sysone.entities.Equipo;
import com.sysone.entities.HistorialJugadorEquipo;
import com.sysone.entities.Jugador;
import com.sysone.entities.Posicion;

@Service
public interface IEquipoService {

	public List<Equipo> findAll(Sort sort);
	public Optional<Equipo> findById(String cuit);
	public Equipo save(Equipo equipo);
	public void delete(String cuit);
	public List<Jugador> listarJugadores(String cuit);
	public List<Jugador> listarJugadores(String cuit, LocalDate fecha);
	public List<HistorialJugadorEquipoDto> listarHistoriales(String cuit);
	public HistorialJugadorEquipoDto listarHistorialPorId(String id);
	public List<Jugador> listarJugadoresPorPosicion(String cuit, Posicion posicion);
	public List<Jugador> listarJugadoresPorPosicion(String cuit, Posicion posicion, LocalDate fecha);
	public HistorialJugadorEquipoDto entityToDto(HistorialJugadorEquipo historial);
	
}
