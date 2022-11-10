package com.nicolas.controller.responses;

import java.time.LocalDate;

import com.nicolas.entities.Time;

public class TimeResponse {
	
	private Integer id;
	private String nome;
	private LocalDate dataDeFundacao;
	private String cidadeDoTime;
	private String estadoDoTime;
	private int vitorias = 0;
	private int empates = 0;
	private int derrotas = 0;

	public TimeResponse(Time time) {
		this.id = time.getId();
		this.nome = time.getNome();
		this.dataDeFundacao = time.getDataDeFundacao();
		this.cidadeDoTime = time.getCidadeDoTime();
		this.estadoDoTime = time.getEstadoDoTime();
		this.vitorias = time.getVitorias();
		this.empates = time.getEmpates();
		this.derrotas = time.getDerrotas();
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

	public int getVitorias() {
		return vitorias;
	}

	public int getEmpates() {
		return empates;
	}

	public int getDerrotas() {
		return derrotas;
	}
}
