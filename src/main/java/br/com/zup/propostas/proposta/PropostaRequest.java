package br.com.zup.propostas.proposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.propostas.endereco.Endereco;
import br.com.zup.propostas.endereco.EnderecoRepository;
import br.com.zup.propostas.validation.CPFOuCNPJ;

public class PropostaRequest {
	@CPFOuCNPJ
	private String documento;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String nome;
	
	@NotNull
	private Long idEndereco;
	
	@NotNull @Positive
	private BigDecimal salario;

	public PropostaRequest(String documento, @Email @NotBlank String email, @NotBlank String nome,
			@NotNull Long idEndereco, @NotNull @Positive BigDecimal salario) {
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.idEndereco = idEndereco;
		this.salario = salario;
	}

	public Proposta toModel(EnderecoRepository enderecoRepository) {
		@NotNull Endereco endereco = enderecoRepository.getOne(this.idEndereco);
		return new Proposta(documento, email, nome, endereco,salario);
	}
	
}
