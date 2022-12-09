package com.nicolas.controller.requests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.nicolas.commons.validators.FieldFinder;
import com.nicolas.entities.Jogador;
import com.nicolas.entities.Partida;
import com.nicolas.entities.Time;
import com.nicolas.repositories.TimeRepository;

public class PartidaRequest {
	
	@NotNull
	@FieldFinder(domainClass = Time.class, fieldName = "id")
	private Integer idTimeMandante;
	@NotNull
	@FieldFinder(domainClass = Time.class, fieldName = "id")
	private Integer idTimeVisitante;
	@NotNull
	private LocalDateTime horaDoInicioDoJogo;
	@NotNull
	private LocalDateTime horaDoFimDoJogo;
	private Integer golsMarcadosPeloMandante = 0;
	private Integer golsMarcadosPeloVisitante = 0;
	private Integer cartoesAmarelosProMandante = 0;
	private Integer cartoesAmarelosProVisitante = 0;
	private Integer cartoesVermelhosProMandante = 0;
	private Integer cartoesVermelhosProVisitante = 0;
	
	private List<@FieldFinder(domainClass = Jogador.class, fieldName = "id") Integer> jogadoresGols = new ArrayList<>();
	private List<@FieldFinder(domainClass = Jogador.class, fieldName = "id") Integer> jogadoresGolsContra = new ArrayList<>();
	
	public Integer getIdTimeMandante() {
		return idTimeMandante;
	}
	public Integer getIdTimeVisitante() {
		return idTimeVisitante;
	}
	public LocalDateTime getHoraDoInicioDoJogo() {
		return horaDoInicioDoJogo;
	}
	public LocalDateTime getHoraDoFimDoJogo() {
		return horaDoFimDoJogo;
	}
	public Integer getGolsMarcadosPeloMandante() {
		return golsMarcadosPeloMandante;
	}
	public Integer getGolsMarcadosPeloVisitante() {
		return golsMarcadosPeloVisitante;
	}
	public Integer getCartoesAmarelosProMandante() {
		return cartoesAmarelosProMandante;
	}
	public Integer getCartoesAmarelosProVisitante() {
		return cartoesAmarelosProVisitante;
	}
	public Integer getCartoesVermelhosProMandante() {
		return cartoesVermelhosProMandante;
	}
	public Integer getCartoesVermelhosProVisitante() {
		return cartoesVermelhosProVisitante;
	}
	public List<Integer> getJogadoresGols() {
		return jogadoresGols;
	}
	public List<Integer> getJogadoresGolsContra() {
		return jogadoresGolsContra;
	}
	
	public Partida converter(TimeRepository timeRepository) {
		
		Time timeMandante = timeRepository.findById(idTimeMandante).get();
		Time timeVisitante = timeRepository.findById(idTimeVisitante).get();
		
		return new Partida(timeMandante, timeVisitante, horaDoInicioDoJogo, horaDoFimDoJogo, golsMarcadosPeloMandante, golsMarcadosPeloVisitante,
				cartoesAmarelosProMandante, cartoesAmarelosProVisitante, cartoesVermelhosProMandante, cartoesVermelhosProVisitante);
	}
}