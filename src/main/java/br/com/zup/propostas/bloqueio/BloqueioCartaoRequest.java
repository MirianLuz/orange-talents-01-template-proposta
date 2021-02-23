package br.com.zup.propostas.bloqueio;

public class BloqueioCartaoRequest {

	 private String sistemaResponsavel;

	public BloqueioCartaoRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}
	 
}
