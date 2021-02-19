package br.com.zup.propostas.biometria;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BiometriaResponse {

	 private String biometria;

	 @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public BiometriaResponse(Biometria biometria) {
		this.biometria = biometria.getBiometria();
	}
	 
	public String getBiometriaDeserializada() {
		 byte[] decode = Base64.getDecoder().decode(biometria.getBytes());
		 String mensagemDecodificada = new String(decode);
		 return(mensagemDecodificada);
		
		}
}
