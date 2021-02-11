package br.com.zup.propostas.endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EnderecoRequest {
	@NotBlank
	private String rua;
	
	@NotNull
	private int numero;
	
	@NotBlank
	private String complemento;
	
	@NotBlank	
	private String cidade;
	
	@NotBlank
	private String estado;

	public EnderecoRequest(@NotBlank String rua, @NotNull int numero, @NotBlank String complemento,
			@NotBlank String cidade, @NotBlank String estado) {
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	public Endereco toModel() {
		return new Endereco(rua, numero, complemento, cidade, estado);
	}
	
}
