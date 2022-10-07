package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

}
