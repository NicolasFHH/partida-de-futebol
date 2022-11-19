package com.nicolas.controller.requests;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.nicolas.commons.validators.FieldFinder;
import com.nicolas.entities.Jogador;
import com.nicolas.entities.Time;

public class AdicionaJogadorRequest {
	
	@NotNull
	@FieldFinder(domainClass = Time.class, fieldName = "id")
	private int idTime;
	@NotNull
	private List<@FieldFinder(domainClass = Jogador.class, fieldName = "id") Integer> idJogador = new ArrayList<>();
	
	public int getIdTime() {
		return idTime;
	}
	public List<Integer> getIdJogador() {
		return idJogador;
	}
}