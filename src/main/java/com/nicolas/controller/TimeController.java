package com.nicolas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.nicolas.controller.requests.TimeRequest;
import com.nicolas.controller.responses.TimeResponse;
import com.nicolas.entities.Time;
import com.nicolas.repositories.JogadorRepository;
import com.nicolas.repositories.TimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {
	
	@Autowired
	private TimeRepository timeRepository;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoTime(@RequestBody @Valid TimeRequest timeRequest) {
		Time time = timeRequest.converter(jogadorRepository);
		timeRepository.save(time);
	}
	
	@GetMapping
	public ResponseEntity<List<TimeResponse>> findAll() {
		List<Time> times = timeRepository.findAll();
		List<TimeResponse> timesResponse = times.stream().map(TimeResponse::new).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(timesResponse);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TimeResponse> getTimePorId(@PathVariable int id) {
		Optional<Time> time = timeRepository.findById(id);
		if (time.isPresent()) {
			return ResponseEntity.ok().body(new TimeResponse(time.get()));
		}
		return ResponseEntity.notFound().build();
	}
}
