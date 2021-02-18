package com.sysone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sysone.entities.HistorialJugadorEquipo;

public interface IHistorialJugadorEquipoRepository extends JpaRepository<HistorialJugadorEquipo, String> {
	
	@Query("select h from HistorialJugadorEquipo h where h.jugador.dni = :dni")
	public List<HistorialJugadorEquipo> listarPorJugador(@Param("dni") String dni);
	
	@Query("select h from HistorialJugadorEquipo h where h.equipo.cuit = :cuit")
	public List<HistorialJugadorEquipo> listarPorEquipo(@Param("cuit") String cuit);
	
}
