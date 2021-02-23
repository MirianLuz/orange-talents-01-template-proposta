package br.com.zup.propostas.bloqueio;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.propostas.cartao.Cartao;

@Entity
public class BloqueioCartao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Cartao.class)
    @JoinColumn(name = "cartao_id")
	private Cartao cartao;
	
	@NotBlank
	private String ip;
	
	@NotBlank
	private String userAgent;
	
	private LocalDateTime instanteBloqueio;
	
	@Deprecated
	public BloqueioCartao() {
	}

	public BloqueioCartao(@NotNull Cartao cartao, @NotBlank String ip, @NotBlank String userAgent) {
		this.cartao = cartao;
		this.ip = ip;
		this.userAgent = userAgent;
		this.instanteBloqueio = LocalDateTime.now();
	}

	public LocalDateTime getInstanteBloqueio() {
		return instanteBloqueio;
	}

}
