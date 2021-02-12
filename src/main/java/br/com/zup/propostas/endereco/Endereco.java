package br.com.zup.propostas.endereco;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
public class Endereco {
	 
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
	
	@Deprecated
	public Endereco() {
	}

	public Endereco(@NotBlank String cep, @NotBlank String logradouro, @NotNull int numero,
			@NotBlank String complemento, @NotBlank String bairro, @NotBlank String cidade, @NotBlank String estado) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Endereco [cep=" + cep + ", logradouro=" + logradouro + ", numero=" + numero + ", complemento="
				+ complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado + "]";
	}
}
