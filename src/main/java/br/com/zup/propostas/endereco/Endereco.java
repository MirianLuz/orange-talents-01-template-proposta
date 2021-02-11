package br.com.zup.propostas.endereco;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@Deprecated
	public Endereco() {
	}

	public Endereco(@NotBlank String rua, @NotNull int numero, @NotBlank String complemento, @NotBlank String cidade,
			@NotBlank String estado) {
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Endereco [id=" + id + ", rua=" + rua + ", numero=" + numero + ", complemento=" + complemento
				+ ", cidade=" + cidade + ", estado=" + estado + "]";
	}
	
}
