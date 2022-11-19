package com.nicolas.controller.requests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.nicolas.commons.validators.FieldFinder;
import com.nicolas.commons.validators.UniqueValue;
import com.nicolas.entities.Jogador;
import com.nicolas.entities.Time;
import com.nicolas.repositories.JogadorRepository;

public class TimeRequest {
	
	@NotBlank
	@UniqueValue(domainClass = Time.class, fieldName = "nome")
	private String nome;
	@NotNull
	@Past
	private LocalDate dataDeFundacao;
	@NotBlank
	private String cidadeDoTime;
	@NotBlank
	private String estadoDoTime;
	private List<@FieldFinder(domainClass = Jogador.class, fieldName = "id") Integer> jogadores = new ArrayList<>();
	
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
	
	public List<Integer> getJogadores() {
		return jogadores;
	}
	
	public Time converter(JogadorRepository jogadorRepository) {
		List<Jogador> listaJogadores = jogadores.stream().map(jogador -> jogadorRepository.findById(jogador).get()).collect(Collectors.toList());
		return new Time(nome, dataDeFundacao, cidadeDoTime, estadoDoTime, listaJogadores);
	}
}
