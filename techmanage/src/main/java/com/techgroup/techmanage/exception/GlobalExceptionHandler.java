package com.techgroup.techmanage.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex)
	{
		return new ResponseEntity<>("Erro interno no servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex)
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado em banco de dados");
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex)
	{
		String handledErrorMsg = ex.getConstraintViolations()
				.stream()
				.map(violation -> violation.getMessage())
				.collect(Collectors.joining("\n"));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handledErrorMsg);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<String> handleTransactionException(TransactionSystemException ex)
	{
		Throwable cause = ex.getMostSpecificCause();
		
		if(cause instanceof ConstraintViolationException)
			return handleConstraintViolationException((ConstraintViolationException) cause);

		return handleGenericException(ex);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleJsonParseException(HttpMessageNotReadableException ex)
	{
		Throwable cause = ex.getCause();
		
		if(cause instanceof InvalidFormatException)
		{
			InvalidFormatException rootCause = (InvalidFormatException) cause;
			
			if(rootCause.getTargetType().isEnum())
			{
				String enumValidValues = Arrays.toString(rootCause.getTargetType().getEnumConstants());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Valor inválido para campo '" + rootCause.getPath().get(0).getFieldName() +
							   "'. Os valores permitidos são: " + enumValidValues);
			}
			
			if (rootCause.getPath().get(0).getFieldName().equals("birthDate")) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Formato de data inválido. Use o padrão yyyy-MM-dd.");
	        }
			
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Erro ao processar requisição. Verifique os dados enviados.");
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex)
	{
		String paramName = ex.getName();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Formato inválido para o campo '" + paramName + "'. O campo deve estar preenchido com um número inteiro.");
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex)
	{
		return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
	}
}
