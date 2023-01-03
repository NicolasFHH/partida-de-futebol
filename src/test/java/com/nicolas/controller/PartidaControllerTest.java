package com.nicolas.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.nicolas.entities.Partida;
import com.nicolas.entities.Time;
import com.nicolas.entities.enums.Posicao;
import com.nicolas.repositories.JogadorRepository;
import com.nicolas.repositories.PartidaRepository;
import com.nicolas.repositories.TimeRepository;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class PartidaControllerTest {

private String uri = "/partidas";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TimeRepository timeRepository;
	
	@Autowired
	private JogadorRepository jogadorRepository;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@BeforeEach
	void setup() {
		Jogador jogador1 = new Jogador("Vitão", 22, Posicao.ZAGUEIRO);
		jogadorRepository.save(jogador1);
		Jogador jogador2 = new Jogador("Pedro Henrique", 32, Posicao.ATACANTE);
		jogadorRepository.save(jogador2);
		Jogador jogador3 = new Jogador("Messi", 35, Posicao.ATACANTE);
		jogadorRepository.save(jogador3);
		Jogador jogador4 = new Jogador("Tielemans", 25, Posicao.MEIA);
		jogadorRepository.save(jogador4);
		@SuppressWarnings("unused")
		Jogador jogador5 = new Jogador("Rashford", 24, Posicao.ATACANTE);
		
		Time time1 = new Time("Internacional", LocalDate.of(1909, 04, 04), "Porto Alegre", "Rio Grande do Sul", List.of(jogador1, jogador2));
		timeRepository.save(time1);
		Time time2 = new Time("Grêmio", LocalDate.of(1903, 10, 15), "Porto Alegre", "Rio Grande do Sul", List.of(jogador3, jogador4));
		timeRepository.save(time2);
		
		Partida partida = new Partida(time1, time2, LocalDateTime.now(), LocalDateTime.now().plusMinutes(90), 2, 1, 4, 3, 0, 1);
		partidaRepository.save(partida);
	}
	
	@Test
	void deveCriarPartidaQuandoEstaTudoCerto() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
			+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
			+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
			+ "\"jogadoresGols\" : [1, 1], \"jogadoresGolsContra\" : [4] }";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void naoDeveCriarPartidaComHoraFinalDoJogoAntesDaHoraInicial() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T20:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 1], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComSomaDeGolsDosTimesMaiorDaSomaDosGolsMarcadosPorJogadores() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 1, "
				+ "\"jogadoresGols\" : [1, 2], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComSomaDeGolsDosTimesMenorDaSomaDosGolsMarcadosPorJogadores() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 2, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 2], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComJogadorQueNaoPertenceAosTimesMarcandoGol() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 5], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComIdDeTimeMandanteNulo() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : , \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 5], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComIdDeTimeVisitanteNulo() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : ,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 5], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComIdDeTimeMandanteQueNaoExiste() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 52, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 5], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComIdDeTimeVisitanteQueNaoExiste() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 37,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 5], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComHoraInicialDoJogoNula() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"\", \"horaDoFimDoJogo\" : \"2022-10-10T19:30:15\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 1], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveCriarPartidaComHoraFinalDoJogoNula() throws Exception {
		String partidaDTO = "{ \"idTimeMandante\" : 1, \"idTimeVisitante\" : 2,  \"horaDoInicioDoJogo\" : "
				+ "\"2022-10-10T17:30:15\", \"horaDoFimDoJogo\" : \"\", "
				+ "\"golsMarcadosPeloMandante\" : 3, \"golsMarcadosPeloVisitante\" : 0, "
				+ "\"jogadoresGols\" : [1, 1], \"jogadoresGolsContra\" : [4] }";
				
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(partidaDTO).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveRetornarListaDePartidasComMetodoFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void deveRetornarUmaPartidaComMetodoFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void nãoDeveRetornarUmaPartidaComIdInexistenteComMetodoFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri + "/34"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
