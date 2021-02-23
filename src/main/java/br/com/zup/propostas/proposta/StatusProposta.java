package br.com.zup.propostas.proposta;

public enum StatusProposta {
	NAO_ELEGIVEL, ELEGIVEL;

	public static StatusProposta resultadoPara(String solicitacao) {
		if(solicitacao.equals("COM_RESTRICAO")) {
			return NAO_ELEGIVEL;
		}
		
		return ELEGIVEL;
	}
}
