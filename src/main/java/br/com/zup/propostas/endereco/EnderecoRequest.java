package br.com.zup.propostas.endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EnderecoRequest {
	
	@NotBlank
	private String cep;
	
	@NotBlank
	private String logradouro;
	
	@NotNull
	private int numero;
	
	@NotBlank
	private String complemento;
	
	@NotBlank
    private String bairro;
	
	@NotBlank	
	private String cidade;
	
	@NotBlank
	private String estado;
	
	public EnderecoRequest(@NotBlank String cep, @NotBlank String logradouro, @NotNull int numero,
			@NotBlank String complemento, @NotBlank String bairro, @NotBlank String cidade, @NotBlank String estado) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}

	public Endereco toModel() {
		return new Endereco(cep, logradouro, numero, complemento, bairro, cidade, estado);
	}
}
