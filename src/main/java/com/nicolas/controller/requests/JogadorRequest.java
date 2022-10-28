package com.nicolas.controller.requests;

import com.nicolas.entities.Jogador;
import com.nicolas.entities.enums.Posicao;

public class JogadorRequest {
	
	private String nome;
	private int idade;
	private Posicao posicao;
	
	public String getNome() {
		return nome;
	}

	public int getIdade() {
		return idade;
	}

	public Posicao getPosicao() {
		return posicao;
	}
	
	public Jogador converter() {
	    return new Jogador(nome, idade, posicao);
	}
}