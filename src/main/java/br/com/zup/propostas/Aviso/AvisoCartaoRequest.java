package br.com.zup.propostas.Aviso;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zup.propostas.cartao.Cartao;

public class AvisoCartaoRequest {
	
	@NotBlank
	@JsonProperty
	private String destino;
	
	@Future
    @NotNull
    @JsonProperty
	private LocalDate validoAte;
	
	public AvisoCartaoRequest(@NotBlank String destino, @Future @NotNull LocalDate validoAte) {
		this.destino = destino;
		this.validoAte = validoAte;
	}

	public Aviso toModel(AvisoCartaoRequest request, String ip, String userAgent, Cartao cartao) {
        return new Aviso(this.destino, this.validoAte, ip, userAgent, cartao);
    }
}
