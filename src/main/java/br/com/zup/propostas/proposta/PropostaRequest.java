package br.com.zup.propostas.proposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.propostas.endereco.EnderecoRequest;

public class PropostaRequest {
//	@CPFOuCNPJ
	private String documento;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String nome;
	
	@NotNull
	private EnderecoRequest endereco;
	
	@NotNull @Positive
	private BigDecimal salario;

	public PropostaRequest(String documento, @Email @NotBlank String email, @NotBlank String nome,
			@NotNull EnderecoRequest endereco, @NotNull @Positive BigDecimal salario) {
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public String getDocumento() {
		return documento;
	}

	public Proposta toModel() {
		return new Proposta(documento, email, nome, endereco.toModel(),salario);
	}
}
