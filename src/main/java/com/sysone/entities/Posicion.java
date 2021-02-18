package com.sysone.entities;

public enum Posicion {

	DELANTERO("delantero"),
	DEFENSOR("defensor"),
	MEDIOCAMPISTA("mediocampista"),
	ARQUERO("arquero");
	
	private String nombrePosicion;
	
	private Posicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}
	
	public String getNombrePosicion() {
		return nombrePosicion;
	}
	
}
