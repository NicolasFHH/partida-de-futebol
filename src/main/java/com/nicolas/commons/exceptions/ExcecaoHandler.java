package com.nicolas.commons.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcecaoHandler {
	
	@ExceptionHandler(FutebolApiException.class)
	public ResponseEntity<ErroPadrao> tratamentoFutebolApiException(FutebolApiException exception) {
		LocalDateTime instante = LocalDateTime.now();
		ErroPadrao erro = new ErroPadrao(exception.getMensagem(), exception.getCampo(), instante);
		return ResponseEntity.status(exception.getHttpStatus()).body(erro);
	}

}
