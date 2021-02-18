package com.sysone.dtos;

import java.time.LocalDate;

import com.sysone.entities.Posicion;

public class HistorialJugadorEquipoDto {
	
	private String id;
	
	private EquipoDto equipo;
	
	private JugadorDto jugador;
	
	private LocalDate fecha_inicio;
	
	private LocalDate fecha_fin;
	
	private Posicion posicion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EquipoDto getEquipo() {
		return equipo;
	}

	public void setEquipo(EquipoDto equipoDto) {
		this.equipo = equipoDto;
	}

	public JugadorDto getJugador() {
		return jugador;
	}

	public void setJugador(JugadorDto jugadorDto) {
		this.jugador = jugadorDto;
	}

	public LocalDate getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(LocalDate fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public LocalDate getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(LocalDate fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}
	
}
