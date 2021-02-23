package br.com.zup.propostas.bloqueio;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.zup.propostas.Aviso.AvisoCartaoResponse;
import br.com.zup.propostas.carteiras.CarteiraRequest;
import br.com.zup.propostas.carteiras.CarteiraResponse;
import br.com.zup.propostas.Aviso.AvisoCartaoRequest;

@FeignClient(name="consultasCartaoClient", url="${service.cartao.url}")
public interface ConsultasCartaoClient {
	
	@PostMapping("/api/cartoes/{id}/bloqueios")
	BloqueioCartaoResponse consultaBloqueio(@PathVariable String id, @RequestBody BloqueioCartaoRequest bloqueioRequest);

	@PostMapping("/api/cartoes/{id}/avisos")
	AvisoCartaoResponse avisos(@PathVariable String id, @RequestBody AvisoCartaoRequest avisoCartaoRequest);
	
	@PostMapping("/api/cartoes/{id}/carteiras")
	CarteiraResponse associaCarteira(@PathVariable String id, @RequestBody CarteiraRequest carteiraRequest);
}
