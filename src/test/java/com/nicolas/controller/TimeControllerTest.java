package com.nicolas.controller;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nicolas.entities.Jogador;
import com.nicolas.entities.Time;
import com.nicolas.entities.enums.Posicao;
import com.nicolas.repositories.JogadorRepository;
import com.nicolas.repositories.TimeRepository;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class TimeControllerTest {
	
	private String uri = "/times";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TimeRepository timeRepository;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@BeforeEach
	void setup() {
		Jogador jogador1 = new Jogador("Vitão", 22, Posicao.ZAGUEIRO);
		jogadorRepository.save(jogador1);
		Jogador jogador2 = new Jogador("Pedro Henrique", 32, Posicao.ATACANTE);
		jogadorRepository.save(jogador2);
		Time time = new Time("Internacional", LocalDate.of(1909, 04, 04), "Porto Alegre", "Rio Grande do Sul", List.of(jogador1));
		timeRepository.save(time);
	}
	
	@Test
	void naoDeveCriarTimeSemNomeOuNulo() throws Exception {
		String timeDTO = "{\"nome\": \"\", \"dataDeFundacao\": \"1884-01-01\", \"cidadeDoTime\": \"Leicester\", \"estadoDoTime\": \"East Midlands\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(timeDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarTimeSemData() throws Exception {
		String timeDTO = "{\"nome\": \"Leicester City\", \"dataDeFundacao\": \"\", \"cidadeDoTime\": \"Leicester\", \"estadoDoTime\": \"East Midlands\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(timeDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarTimeSemCidade() throws Exception {
		String timeDTO = "{\"nome\": \"Leicester City\", \"dataDeFundacao\": \"1884-01-01\", \"cidadeDoTime\": \"\", \"estadoDoTime\": \"East Midlands\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(timeDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarTimeSemEstado() throws Exception {
		String timeDTO = "{\"nome\": \"Leicester City\", \"dataDeFundacao\": \"1884-01-01\", \"cidadeDoTime\": \"Leicester\", \"estadoDoTime\": \"\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(timeDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarTimeComMesmoNome() throws Exception {
		String timeDTO = "{\"nome\": \"Internacional\", \"dataDeFundacao\": \"1884-01-01\", \"cidadeDoTime\": \"Leicester\", \"estadoDoTime\": \"East Midlands\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(timeDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void DeveCriarTimeQuandoEstaTudoCerto() throws Exception {
		String timeDTO = "{\"nome\": \"Leicester City\", \"dataDeFundacao\": \"1884-01-01\", \"cidadeDoTime\": \"Leicester\", \"estadoDoTime\": \"East Midlands\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(timeDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void deveRetornarListaDeTimesComMetodoFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void deveRetornarUmTimeComMetodoFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void nãoDeveRetornarUmTimeComIdInexistenteComMetodoFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/34"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void deveRetornarOTimePeloNome() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/Internacional"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void nãoDeveRetornarOTimeEscolhidoQuandoONomeDeleNaoExiste() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/Liverpool"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void deveRetornarJogadorAdicionado() throws Exception {
		String adicionaJogadorDTO = "{\"idTime\": 1, \"idJogador\": [2]}";
		mockMvc.perform(MockMvcRequestBuilders.patch(uri).content(adicionaJogadorDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void naoDeveRetornarJogadorAdicionadoComIdDeTimeInexistente() throws Exception {
		String adicionaJogadorDTO = "{\"idTime\": 99, \"idJogador\": [2]}";
		mockMvc.perform(MockMvcRequestBuilders.patch(uri).content(adicionaJogadorDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveRetornarJogadorAdicionadoComIdDeJogadorInexistente() throws Exception {
		String adicionaJogadorDTO = "{\"idTime\": 1, \"idJogador\": [200]}";
		mockMvc.perform(MockMvcRequestBuilders.patch(uri).content(adicionaJogadorDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}