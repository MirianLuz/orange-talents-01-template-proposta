package br.com.zup.propostas.carteiras;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import br.com.zup.propostas.cartao.Cartao;

public class CarteiraRequest {

	@Email
	private String email;
	
	@NotNull
	private TiposCarteira tiposCarteira;

	public CarteiraRequest(@Email String email, @NotNull TiposCarteira tiposCarteira) {
		this.email = email;
		this.tiposCarteira = tiposCarteira;
	}

	public String getEmail() {
		return email;
	}

	public TiposCarteira getTiposCarteira() {
		return tiposCarteira;
	}

	public Carteira toModel(Cartao cartao) {
		return new Carteira(cartao, this.email, this.tiposCarteira);
	}

}
