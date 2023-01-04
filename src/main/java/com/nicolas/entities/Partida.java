package com.nicolas.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Partida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	private Time timeMandante;
	@OneToOne
	private Time timeVisitante;
	@Column(nullable = false)
	@JsonFormat
	(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataDeInicio;
	@Column(nullable = false)
	@JsonFormat
	(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataDoFim;
	private Integer golsDoTimeMandante = 0;
	private Integer golsDoTimeVisitante = 0;
	private Integer cartoesAmarelosDoTimeMandante = 0;	
	private Integer cartoesAmerelosDoTimeVisitante = 0;	
	private Integer cartoesVermelhosDoTimeMandante = 0;	
	private Integer cartoesVermelhosDoTimeVisitante = 0;
	
	public Partida() {
	}
	
	public Partida(Time timeMandante, Time timeVisitante, LocalDateTime dataDeInicio, LocalDateTime dataDoFim,
			Integer golsDoTimeMandante, Integer golsDoTimeVisitante, Integer cartoesAmarelosDoTimeMandante,
			Integer cartoesAmerelosDoTimeVisitante, Integer cartoesVermelhosDoTimeMandante,
			Integer cartoesVermelhosDoTimeVisitante) {
		this.timeMandante = timeMandante;
		this.timeVisitante = timeVisitante;
		this.dataDeInicio = dataDeInicio;
		this.dataDoFim = dataDoFim;
		this.golsDoTimeMandante = golsDoTimeMandante;
		this.golsDoTimeVisitante = golsDoTimeVisitante;
		this.cartoesAmarelosDoTimeMandante = cartoesAmarelosDoTimeMandante;
		this.cartoesAmerelosDoTimeVisitante = cartoesAmerelosDoTimeVisitante;
		this.cartoesVermelhosDoTimeMandante = cartoesVermelhosDoTimeMandante;
		this.cartoesVermelhosDoTimeVisitante = cartoesVermelhosDoTimeVisitante;
	}

	public Integer getId() {
		return id;
	}

	public Time getTimeMandante() {
		return timeMandante;
	}

	public Time getTimeVisitante() {
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