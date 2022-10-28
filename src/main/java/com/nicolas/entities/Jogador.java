package com.nicolas.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.nicolas.entities.enums.Posicao;

@Entity
public class Jogador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private int idade;
	@Enumerated(EnumType.STRING)
	private Posicao posicao;
	private int golsFeitos = 0;
	
	@Deprecated
	public Jogador() {
	}

	public Jogador(String name, int idade, Posicao posicao) {
		this.nome = name;
		this.idade = idade;
		this.posicao = posicao;
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
