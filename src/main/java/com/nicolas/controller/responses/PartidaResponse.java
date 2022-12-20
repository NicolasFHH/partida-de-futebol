package com.nicolas.controller.responses;

import java.time.LocalDateTime;

import com.nicolas.entities.Partida;

public class PartidaResponse {
	
	private Integer id;
	private TimeResponsePartida timeMandante;
	private TimeResponsePartida timeVisitante;
	private LocalDateTime dataDeInicio;
	private LocalDateTime dataDoFim;
	private Integer golsDoTimeMandante;
	private Integer golsDoTimeVisitante;
	private Integer cartoesAmarelosDoTimeMandante;	
	private Integer cartoesAmerelosDoTimeVisitante;	
	private Integer cartoesVermelhosDoTimeMandante;	
	private Integer cartoesVermelhosDoTimeVisitante;
	
	public PartidaResponse(Partida partida) {
		this.id = partida.getId();
		this.timeMandante = new TimeResponsePartida(partida.getTimeMandante());
		this.timeVisitante = new TimeResponsePartida(partida.getTimeVisitante());
		this.dataDeInicio = partida.getDataDeInicio();
		this.dataDoFim = partida.getDataDoFim();
		this.golsDoTimeMandante = partida.getGolsDoTimeMandante();
		this.golsDoTimeVisitante = partida.getGolsDoTimeVisitante();
		this.cartoesAmarelosDoTimeMandante = partida.getCartoesAmarelosDoTimeMandante();
		this.cartoesAmerelosDoTimeVisitante = partida.getCartoesAmerelosDoTimeVisitante();
		this.cartoesVermelhosDoTimeMandante = partida.getCartoesVermelhosDoTimeMandante();
		this.cartoesVermelhosDoTimeVisitante = partida.getCartoesVermelhosDoTimeVisitante();
	}

	public Integer getId() {
		return id;
	}

	public TimeResponsePartida getTimeMandante() {
		return timeMandante;
	}

	public TimeResponsePartida getTimeVisitante() {
		return timeVisitante;
	}

	public LocalDateTime getDataDeInicio() {
		return dataDeInicio;
	}

	public LocalDateTime getDataDoFim() {
		return dataDoFim;
	}

	public Integer getGolsDoTimeMandante() {
		return golsDoTimeMandante;
	}

	public Integer getGolsDoTimeVisitante() {
		return golsDoTimeVisitante;
	}

	public Integer getCartoesAmarelosDoTimeMandante() {
		return cartoesAmarelosDoTimeMandante;
	}

	public Integer getCartoesAmerelosDoTimeVisitante() {
		return cartoesAmerelosDoTimeVisitante;
	}

	public Integer getCartoesVermelhosDoTimeMandante() {
		return cartoesVermelhosDoTimeMandante;
	}

	public Integer getCartoesVermelhosDoTimeVisitante() {
		return cartoesVermelhosDoTimeVisitante;
	}
}
