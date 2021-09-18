package br.com.urso.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	public ValidationError(Long timestamp, Integer status, String error, String msg, String path) {
		super(timestamp, status, error, msg, path);
	}

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> list = new ArrayList<>();
	
	//O nome depois do get é vira o nome da lista
	//ou seja, quando criar o objeto Lista o nome será Erros 
	public List<FieldMessage> getErros(){
		return list;
	}

	public void addError(String fieldName, String message) {
		list.add(new FieldMessage(fieldName, message));
	}
	
}
