package com.nicolas.controller.responses;

import com.nicolas.entities.Jogador;
import com.nicolas.entities.enums.Posicao;

public class JogadorResponse {
	
	private Integer id;
	private String nome;
	private int idade;
	private Posicao posicao;
	private int golsFeitos = 0;
	
	public JogadorResponse(Jogador jogador) {
		this.id = jogador.getId();
		this.nome = jogador.getNome();
		this.idade = jogador.getIdade();
		this.posicao = jogador.getPosicao();
		this.golsFeitos = jogador.getGolsFeitos();
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return nome;
	}

	public int getIdade() {
		return idade;
	}

	public Posicao getPosicao() {
		return posicao;
	}

	public int getGolsFeitos() {
		return golsFeitos;
	}
}
