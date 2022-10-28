package com.nicolas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nicolas.commons.exceptions.FutebolApiException;
import com.nicolas.controller.requests.JogadorRequest;
import com.nicolas.controller.responses.JogadorResponse;
import com.nicolas.entities.Jogador;
import com.nicolas.entities.enums.Posicao;
import com.nicolas.repositories.JogadorRepository;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoJogador(@RequestBody JogadorRequest jogadorRequest) {
		Jogador jogador = jogadorRequest.converter();
		jogadorRepository.save(jogador);
	}
	
	@GetMapping
	public ResponseEntity<List<JogadorResponse>> findAll() {
		List<Jogador> jogadores = jogadorRepository.findAll();
		List<JogadorResponse> jogadoresResponse = jogadores.stream().map(JogadorResponse::new).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(jogadoresResponse);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<JogadorResponse> getJogadorPorId(@PathVariable int id) {
		Optional<Jogador> jogador = jogadorRepository.findById(id);
		if (jogador.isPresent()) {
			return ResponseEntity.ok().body(new JogadorResponse(jogador.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{posicao}/posicao")
	public ResponseEntity<List<JogadorResponse>> getJogadoresDaPosicao(@PathVariable String posicao) {
		
		Posicao posicaoJogador;
		try {
			posicaoJogador = Posicao.valueOf(posicao.toUpperCase());
		} catch(IllegalArgumentException e) {
			throw new FutebolApiException("Não existe a posição " + posicao, "posição", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		List<Jogador> jogador = jogadorRepository.findAllByPosicao(posicaoJogador);
		List<JogadorResponse> jogadoresResponse = jogador.stream().map(JogadorResponse::new).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(jogadoresResponse);
	}
	
	@DeleteMapping(value = "/{id}")
	public HttpStatus deleteById(@PathVariable int id) {
		Optional<Jogador> jogador = jogadorRepository.findById(id);
		if (jogador.isPresent()) {
			jogadorRepository.deleteById(id);
			return HttpStatus.NO_CONTENT;
		}
		return HttpStatus.NOT_FOUND;
	}
}
