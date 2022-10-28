package com.nicolas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicolas.entities.Jogador;
import com.nicolas.entities.enums.Posicao;

public interface JogadorRepository extends JpaRepository<Jogador, Integer> {
	
	List<Jogador> findAllByPosicao(Posicao atacante);
}