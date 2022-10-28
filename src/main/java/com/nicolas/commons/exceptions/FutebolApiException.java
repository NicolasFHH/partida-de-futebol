package com.nicolas.commons.exceptions;

import org.springframework.http.HttpStatus;

public class FutebolApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String mensagem;
	private String campo;
	private HttpStatus httpStatus;
	
	public FutebolApiException(String mensagem, String campo, HttpStatus httpStatus) {
		super(mensagem);
		this.campo = campo;
		this.httpStatus = httpStatus;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
