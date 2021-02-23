package br.com.zup.propostas.proposta;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.propostas.cartao.Cartao;
import br.com.zup.propostas.endereco.Endereco;

@Entity
public class Proposta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@CPFOuCNPJ
	private String documento;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String nome;
	
	@NotNull
	@Embedded
	private Endereco endereco;
	
	@NotNull @Positive
	private BigDecimal salario;

	@Enumerated(EnumType.STRING)
	private StatusProposta status;
	
	@OneToOne(mappedBy = "proposta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Cartao cartao;
	
	@Deprecated
	public Proposta() {
	}

	public Proposta(String documento, @Email @NotBlank String email, @NotBlank String nome, @NotNull Endereco endereco,
			@NotNull @Positive BigDecimal salario) {
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Long getId() {
		return id;
	}
	
	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public StatusProposta getStatus() {
		return status;
	}
	
	public Cartao getCartao() {
		return cartao;
	}

	public void atualizaStatus(String solicitacao) {
		this.status = StatusProposta.resultadoPara(solicitacao);		
	}
	
	public void associaCartao(Cartao cartao) {
        this.cartao = cartao;
    }
	
	@Override
	public String toString() {
		return "Proposta [id=" + id + ", documento=" + documento + ", email=" + email + ", nome=" + nome + ", endereco="
				+ endereco + ", salario=" + salario + ", status=" + status + ", cartao=" + cartao + "]";
	}

}
