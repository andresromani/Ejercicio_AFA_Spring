package com.sysone.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "direcciones")
public class Direccion {

	@Id
	@Column(unique = true, columnDefinition = "VARCHAR(32)")
	private String id = UUID.randomUUID().toString().replace("-", "");
	
	@NotBlank(message = "el campo calle no puede ser nulo ni puede estar vacio")
	@Size(max = 100, message = "el campo calle debe tener entre 1 y 100 caracteres")
	private String calle;
	
	@NotBlank(message = "el campo número no puede ser nulo ni puede estar vacio")
	@Size(max = 5, message = "el campo numero debe tener entre 1 y 100 caracteres")
	private String numero;
	
	@NotBlank(message = "el campo localidad no puede ser nulo ni puede estar vacio")
	@Size(max = 100, message = "el campo localidad debe tener entre 1 y 100 caracteres")
	private String localidad;
	
	@NotBlank(message = "el campo provincia no puede ser nulo ni puede estar vacio")
	@Size(max = 100, message = "el campo provincia debe tener entre 1 y 100 caracteres")
	private String provincia;
	
	public String getCalle() {
		return calle;
	}
	
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
}
