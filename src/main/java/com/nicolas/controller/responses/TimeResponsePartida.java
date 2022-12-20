package com.nicolas.controller.responses;

import java.util.List;
import java.util.stream.Collectors;

import com.nicolas.entities.Time;

public class TimeResponsePartida {
	
	private String nome;
	private List<JogadorResponse> elenco;
	
	public TimeResponsePartida(Time time) {
		this.nome = time.getNome();
		this.elenco = time.getJogadoresDoElenco().stream().map(JogadorResponse::new).collect(Collectors.toList());;
	}

	public String getNome() {
		return nome;
	}

	public List<JogadorResponse> getElenco() {
		return elenco;
	}
}
