package br.com.zup.propostas.carteiras;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import br.com.zup.propostas.cartao.Cartao;

@Entity
public class Carteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Cartao cartao;
	
	@Email
	private String email;
	
    @Enumerated(EnumType.STRING)
    private TiposCarteira tiposCarteira;

	@Deprecated
	public Carteira() {
	}

	public Carteira(@NotNull Cartao cartao, @Email String email, TiposCarteira tiposCarteira) {
		this.cartao = cartao;
		this.email = email;
		this.tiposCarteira = tiposCarteira;
	}

	public Long getId() {
		return id;
	}

	public TiposCarteira getTiposCarteira() {
		return tiposCarteira;
	}

}
