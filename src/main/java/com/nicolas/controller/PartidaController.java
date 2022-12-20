package com.nicolas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nicolas.commons.exceptions.FutebolApiException;
import com.nicolas.controller.requests.PartidaRequest;
import com.nicolas.controller.responses.PartidaResponse;
import com.nicolas.entities.Jogador;
import com.nicolas.entities.Partida;
import com.nicolas.entities.Time;
import com.nicolas.repositories.JogadorRepository;
import com.nicolas.repositories.PartidaRepository;
import com.nicolas.repositories.TimeRepository;

@RestController
@RequestMapping("/partidas")
public class PartidaController {
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private TimeRepository timeRepository;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novaPartida(@RequestBody @Valid PartidaRequest partidaRequest) {
		
		validaPartidaRequest(partidaRequest);
		
		Partida partida = partidaRequest.converter(timeRepository);
		partidaRepository.save(partida);
		
		atualizaEstatisticasDeJogador(partidaRequest);
		atualizaEstatisticasDeTimes(partidaRequest);
	}

	private void validaPartidaRequest(@Valid PartidaRequest partidaRequest) {
		
		LocalDateTime dataDoInicio = partidaRequest.getHoraDoInicioDoJogo();
		LocalDateTime dataDoFim = partidaRequest.getHoraDoFimDoJogo();
		 	
		if (dataDoFim.isBefore(dataDoInicio)) {
			throw new FutebolApiException("A data final não pode ser antes da inicial ", "Hora fim", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (partidaRequest.getGolsMarcadosPeloMandante() + partidaRequest.getGolsMarcadosPeloVisitante() != 
				partidaRequest.getJogadoresGols().size() + partidaRequest.getJogadoresGolsContra().size()) {
			throw new FutebolApiException("O número de gols e o número de id's de jogadores que marcaram gols"
					+ " não correspondem", "Gols", HttpStatus.BAD_REQUEST);
		}
		
		Time timeMandante = timeRepository.findById(partidaRequest.getIdTimeMandante()).get();
		Time timeVisitante = timeRepository.findById(partidaRequest.getIdTimeVisitante()).get();
		
		List<Jogador> jogadoresGols = Stream.concat(partidaRequest.getJogadoresGols().stream(), partidaRequest.getJogadoresGolsContra().stream())
				.map(id -> jogadorRepository.findById(id).get())
				.collect(Collectors.toList());
		
		for (Jogador jogador : jogadoresGols) {
			if (!timeMandante.getJogadoresDoElenco().contains(jogador) && !timeVisitante.getJogadoresDoElenco().contains(jogador)) {
				throw new FutebolApiException("Jogador não pertence ao elenco", null, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}	
	}
	
	private void atualizaEstatisticasDeJogador(@Valid PartidaRequest partidaRequest) {

		List<Jogador> jogadoresGols = partidaRequest.getJogadoresGols().stream()
				.map(id -> jogadorRepository.findById(id).get())
				.collect(Collectors.toList());
		
		for (Jogador jogador : jogadoresGols) {
			jogador.adicionaGol();
		}
		
		jogadorRepository.saveAll(jogadoresGols);
	}
	
	private void atualizaEstatisticasDeTimes(@Valid PartidaRequest partidaRequest) {
		
		Time timeMandante = timeRepository.findById(partidaRequest.getIdTimeMandante()).get();
		Time timeVisitante = timeRepository.findById(partidaRequest.getIdTimeVisitante()).get();
		
		if (partidaRequest.getGolsMarcadosPeloMandante() > partidaRequest.getGolsMarcadosPeloVisitante()) {
			timeMandante.adicionaVitoria();
			timeVisitante.adicionaDerrota();
			
		} else if (partidaRequest.getGolsMarcadosPeloMandante() < partidaRequest.getGolsMarcadosPeloVisitante()) {
			timeMandante.adicionaDerrota();
			timeVisitante.adicionaVitoria();
			
		} else {
			timeMandante.adicionaEmpate();
			timeVisitante.adicionaEmpate();
		}
		
		timeRepository.save(timeMandante);
		timeRepository.save(timeVisitante);
	}
	
	@GetMapping
	public ResponseEntity<List<PartidaResponse>> findAll() {
		List<Partida> partidas = partidaRepository.findAll();
		List<PartidaResponse> partidasResponse = partidas.stream().map(PartidaResponse::new).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(partidasResponse);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PartidaResponse> getPartidaPorId(@PathVariable int id) {
		Optional<Partida> partida = partidaRepository.findById(id);
		if (partida.isPresent()) {
			return ResponseEntity.ok().body(new PartidaResponse(partida.get()));
		}
		return ResponseEntity.notFound().build();
	}
}