package br.com.zup.propostas.biometria;

import java.util.Base64;

import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonCreator;


public class BiometriaRequest {
	
	@NotBlank
	private String biometria;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public BiometriaRequest(@NotBlank String biometria) {
		this.biometria = biometria;
	}
	

	public String getBiometria() {
		
		return biometria = Base64.getEncoder().encodeToString(biometria.getBytes());
	}
	
}
