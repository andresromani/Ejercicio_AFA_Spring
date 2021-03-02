package com.sysone.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "integrantes")
public class Integrante {

	@Id
	@Column(unique = true)
	@Size(min = 8, max = 8, message = "el campo dni debe tener 8 digitos")
	private String dni;
	
	@NotNull(message = "el campo nombre no puede ser null")
	@Size(min = 2, max = 50, message = "el campo nombre debe tener entre 2 y 50 caracteres")
	private String nombre;
	
	@NotNull(message = "el campo apellido no puede ser null")
	@Size(min = 2, max = 50, message = "el campo apellido debe tener entre 2 y 50 caracteres")
	private String apellido;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;
	
	@NotNull(message = "el campo telefono no puede ser null")
	@Size(min = 8, max = 30, message = "el campo telefono debe tener entre 8 y 20 caracteres")
	private String telefono;
	
	@NotBlank(message = "el campo email no puede ser null")
	@Email(message = "el campo email debe ser un email valido")
	private String email;
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	
}
