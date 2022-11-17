package com.nicolas.controller.requests;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class AdicionaJogadorRequest {
	
	@NotNull
	private int idTime;
	@NotNull
	private List<Integer> idJogador = new ArrayList<>();
	
	public int getIdTime() {
		return idTime;
	}
	public List<Integer> getIdJogador() {
		return idJogador;
	}
}