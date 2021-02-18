package com.sysone.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "equipos")
public class Equipo {

	@Id
	@Column(unique = true)
	@Size(min = 11, max = 11, message = "el campo cuit debe tener 11 digitos")
	private String cuit;
	
	@NotNull(message = "el campo nombre no puede ser nulo ni puede estar vacio")
	@Size(min = 2, max = 50, message = "el campo nombre debe tener entre 2 y 50 caracteres")
	private String nombre;
	
	@Column(columnDefinition = "DATE")
	@NotNull(message = "el campo fecha_fundacion no puede ser nulo ni puede estar vacio")
	private LocalDate fecha_fundacion;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;
	
	@NotNull(message = "el campo telefono no puede ser nulo ni puede estar vacio")
	@Size(min = 8, max = 30, message = "el campo telefono debe tener entre 8 y 20 caracteres")
	private String telefono;
	
	@NotBlank(message = "el campo email no puede ser nulo ni puede estar vacio")
	@Email(message = "el campo email debe ser un email valido")
	private String email;
	
	@NotBlank(message = "el campo categoria no puede ser nulo ni puede estar vacio")
	@Size(max = 50, message = "el campo categoria debe tener entre 8 y 20 caracteres")
	private String categoria;

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFecha_fundacion() {
		return fecha_fundacion;
	}

	public void setFecha_fundacion(LocalDate fecha_fundacion) {
		this.fecha_fundacion = fecha_fundacion;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
