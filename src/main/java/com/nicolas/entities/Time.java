package com.nicolas.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Time {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private LocalDate dataDeFundacao;
	@Column(nullable = false)
	private String cidadeDoTime;
	@Column(nullable = false)
	private String estadoDoTime;
	@OneToMany
	private List<Jogador> jogadoresDoElenco;
	private int vitorias = 0;
	private int empates = 0;
	private int derrotas = 0;
	
	public Time() {
	}
	
	public Time(String name, LocalDate dataDeFundacao, String cidadeDoTime, String estadoDoTime, List<Jogador> listaJogadores) {
		this.nome = name;
		this.dataDeFundacao = dataDeFundacao;
		this.cidadeDoTime = cidadeDoTime;
		this.estadoDoTime = estadoDoTime;
		this.jogadoresDoElenco = listaJogadores;
	}

	public Integer getId() {
		return id;
	}

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

	public List<Jogador> getJogadoresDoElenco() {
		return jogadoresDoElenco;
	}

	public int getVitorias() {
		return vitorias;
	}

	public int getEmpates() {
		return empates;
	}

	public int getDerrotas() {
		return derrotas;
	}
	
	public void adicionaJogadores(List<Jogador> jogadores) {
		jogadoresDoElenco.addAll(jogadores);
	}
	
	public void adicionaVitoria() {
		vitorias ++;
	}
	
	public void adicionaEmpate() {
		empates ++;
	}
	
	public void adicionaDerrota() {
		derrotas ++;
	}
}
