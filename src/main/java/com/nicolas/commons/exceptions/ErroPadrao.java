package com.nicolas.commons.exceptions;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErroPadrao {
	
	private String mensagem;
	private String campo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy 'T'HH:mm:ss", timezone = "GMT")
	private LocalDateTime instante;
	
	public ErroPadrao(String mensagem, String campo, LocalDateTime instante) {
		this.mensagem = mensagem;
		this.campo = campo;
		this.instante = instante;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public LocalDateTime getInstante() {
		return instante;
	}
}
