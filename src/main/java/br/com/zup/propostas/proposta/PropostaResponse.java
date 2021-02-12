package br.com.zup.propostas.proposta;

import java.math.BigDecimal;

import br.com.zup.propostas.endereco.EnderecoResponse;

public class PropostaResponse {
	
	private String documento;
	
	private String email;
	
	private String nome;
	
	private EnderecoResponse endereco;
	
	private BigDecimal salario;

	private Status status;

	public PropostaResponse(Proposta proposta) {
		this.documento = proposta.getDocumento();
		this.email = proposta.getEmail();
		this.nome = proposta.getNome();
		this.endereco = new EnderecoResponse(proposta.getEndereco());
		this.salario = proposta.getSalario();
		this.status = proposta.getStatus();
	}

	public String getDocumento() {
		return documento;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public EnderecoResponse getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public Status getStatus() {
		return status;
	}

}
