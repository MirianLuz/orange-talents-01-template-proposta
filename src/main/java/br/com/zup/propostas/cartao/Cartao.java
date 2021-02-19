package br.com.zup.propostas.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.propostas.proposta.Proposta;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String numero;
	
	@NotBlank
	private String titular;
	
	private LocalDateTime emitidoEm;
	
	private BigDecimal limite;
	
	@OneToOne
	@NotNull
	private Proposta proposta;
	
	@Deprecated
	public Cartao() {
	}
	
	public Cartao(@NotBlank String numero, @NotBlank String titular, LocalDateTime emitidoEm, BigDecimal limite,
			@NotNull Proposta proposta) {
		this.numero = numero;
		this.titular = titular;
		this.emitidoEm = emitidoEm;
		this.limite = limite;
		this.proposta = proposta;
	}
	
	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public String getTitular() {
		return titular;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public Proposta getProposta() {
		return proposta;
	}
	
}
