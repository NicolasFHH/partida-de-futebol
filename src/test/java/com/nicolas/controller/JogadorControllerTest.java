package com.nicolas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nicolas.entities.Jogador;
import com.nicolas.entities.enums.Posicao;
import com.nicolas.repositories.JogadorRepository;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class JogadorControllerTest {

	private String uri = "/jogadores";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@BeforeEach
	void setup() {
		Jogador jogador = new Jogador("Guilherme", 29, Posicao.ZAGUEIRO);
		jogadorRepository.save(jogador);
	}
	
	@Test
	void naoDeveCriarJogadorSemNomeOuNulo() throws Exception {
		String jogadorDTO = "{\"nome\": \"\", \"idade\": 24, \"posicao\": \"ATACANTE\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(jogadorDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarJogadorForaDoIntervaloDeIdade() throws Exception {
		String jogadorDTO = "{\"nome\": \"Roberto\", \"idade\": 14, \"posicao\": \"ATACANTE\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(jogadorDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarJogadorSemPosicao() throws Exception {
		String jogadorDTO = "{\"nome\": \"Roberto\", \"idade\": 19}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(jogadorDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveCriarJogadorQuandoEstaTudoCerto() throws Exception {
		String jogadorDTO = "{\"nome\": \"Roberto\", \"idade\": 21, \"posicao\": \"ATACANTE\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(jogadorDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void deveRetornarListaDeJogadoresComMetodoFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void deveRetornarUmJogadorComMetodoFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void nãoDeveRetornarUmJogadorComIdInexistenteComMetodoFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/35"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void deveRetornarListaDeJogadoresComMetodoFindByPosicao() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/ATACANTE/posicao"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void nãoDdeveRetornarListaDeJogadoresComMetodoAoInformarPosicaoErradaOuInexistente() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/PONTA-DIREITA/posicao"))
		.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
	}
	
	@Test
	void deveDeletarUmJogadorComMetodoDeleteById() throws Exception {
		MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(uri + "/1"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
		
		assertEquals("\"NO_CONTENT\"", resultado.getResponse().getContentAsString());
	}
	
	@Test
	void nãoDeveDeletarUmJogadorComIdInexistenteComMetodoDeleteById() throws Exception {
		MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(uri + "/35"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		assertEquals("\"NOT_FOUND\"", resultado.getResponse().getContentAsString());
	}
}