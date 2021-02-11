package br.com.zup.propostas.validation;

public class FieldErrorOutputDto {

	public String field;
	public String message;
	
	FieldErrorOutputDto() {	}
	
	public FieldErrorOutputDto(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
	
}
