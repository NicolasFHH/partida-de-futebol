package com.nicolas.controller.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nicolas.entities.Jogador;
import com.nicolas.entities.enums.Posicao;

public class JogadorRequest {
	
	@NotBlank
	private String nome;
	@Min(15)
	@Max(56)
	private int idade;
	@NotNull
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