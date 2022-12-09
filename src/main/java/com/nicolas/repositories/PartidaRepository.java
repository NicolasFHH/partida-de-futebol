package com.nicolas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicolas.entities.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
	
	Optional<Partida> findById(Integer id);
}