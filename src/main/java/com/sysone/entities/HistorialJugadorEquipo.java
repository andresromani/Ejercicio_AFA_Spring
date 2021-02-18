package com.sysone.entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "historial_jugador_equipo")
public class HistorialJugadorEquipo {

	@Id
	@Column(unique = true, columnDefinition = "VARCHAR(32)")
	private String id = UUID.randomUUID().toString().replace("-", "");
	
	@ManyToOne
	@JoinColumn(name = "equipo_cuit", columnDefinition = "VARCHAR(11)")
	private Equipo equipo;
	
	@ManyToOne
	@JoinColumn(name = "jugador_dni", columnDefinition = "VARCHAR(8)")
	private Jugador jugador;
	
	@Column(columnDefinition = "DATE")
	@NotNull(message = "el campo fecha_inicio no puede ser nulo ni puede estar vacio")
	private LocalDate fecha_inicio;
	
	@Column(columnDefinition = "DATE")
	@NotNull(message = "el fecha_fin no puede ser nulo ni puede estar vacio")
	private LocalDate fecha_fin;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(55)")
	@NotNull(message = "el campo posicion no puede ser nulo ni puede estar vacio")
	private Posicion posicion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
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
