package com.sysone.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sysone.entities.Equipo;

public interface IEquipoRepository extends JpaRepository<Equipo, String> {

	@Query("select e from Equipo e")
	public List<Equipo> findAll(Sort sort);
	
}
