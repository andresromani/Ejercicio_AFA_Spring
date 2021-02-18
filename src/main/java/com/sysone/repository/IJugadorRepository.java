package com.sysone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysone.entities.Jugador;

public interface IJugadorRepository extends JpaRepository<Jugador, String> {
	
}
