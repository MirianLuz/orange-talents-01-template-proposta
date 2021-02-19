package br.com.zup.propostas.biometria;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.propostas.cartao.Cartao;
import br.com.zup.propostas.cartao.CartaoRepository;

@RestController
public class BiometriaController {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private BiometriaRepository biometriaRepository;
	
	@PostMapping(value ="/cartoes/{id}/biometria")
	public ResponseEntity<?> criarBiometria(@PathVariable("id") Long id, @RequestBody @Valid BiometriaRequest request,
			UriComponentsBuilder uriBuilder) {
		Cartao cartao = cartaoRepository.getOne(id);
		if(cartao == null) {
			return ResponseEntity.notFound().build();
		}
		
		Biometria biometria = new Biometria(cartao, request.getBiometria());
		biometriaRepository.save(biometria);

		URI uri = uriBuilder.path("/cartoes/{idCartao}/biometrias/{idBiometria}").buildAndExpand(cartao.getId(), biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
	}
}
