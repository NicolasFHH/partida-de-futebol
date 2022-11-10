package com.nicolas.controller.requests;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.nicolas.entities.Jogador;
import com.nicolas.entities.Time;
import com.nicolas.repositories.JogadorRepository;

public class TimeRequest {
	
	@NotBlank
	private String nome;
	@NotNull
	@Past
	private LocalDate dataDeFundacao;
	@NotNull
	private String cidadeDoTime;
	@NotNull
	private String estadoDoTime;
	private List<Integer> jogadores;
	
	public String getNome() {
		return nome;
	}
	
	public LocalDate getDataDeFundacao() {
		return dataDeFundacao;
	}
	
	public String getCidadeDoTime() {
		return cidadeDoTime;
	}
	
	public String getEstadoDoTime() {
		return estadoDoTime;
	}
	
	public Time converter(JogadorRepository jogadorRepository) {
		List<Jogador> listaJogadores = jogadores.stream().map(jogador -> jogadorRepository.findById(jogador).get()).collect(Collectors.toList());
	    return new Time(nome, dataDeFundacao, cidadeDoTime, estadoDoTime, listaJogadores);
	}
}
