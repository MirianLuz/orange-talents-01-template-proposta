package br.com.zup.propostas.Aviso;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.propostas.cartao.Cartao;

@Entity
public class Aviso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Cartao cartao;
	
	@NotBlank
	private String destino;
	
	@NotNull @Future
	private LocalDate dataTerminoViagem;
	
	private LocalDateTime dataAviso = LocalDateTime.now();
	
	@NotBlank
	private String ip;
	
	@NotBlank
	private String userAgent;
	
	@Deprecated
	public Aviso() {
	}

	public Aviso(@NotBlank String destino,
				 LocalDate dataTerminoViagem,
				 @NotBlank String ip, 
				 @NotBlank String userAgent, 
				 @NotNull Cartao cartao) {
		this.destino = destino;
		this.dataTerminoViagem = dataTerminoViagem;
		this.ip = ip;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}

	public LocalDateTime getDataAviso() {
		return dataAviso;
	}

}
