package br.com.zup.propostas.carteiras;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.propostas.bloqueio.ConsultasCartaoClient;
import br.com.zup.propostas.cartao.Cartao;
import br.com.zup.propostas.cartao.CartaoRepository;
import br.com.zup.propostas.cartao.StatusCartao;
import feign.FeignException;

@RestController
public class CarteiraController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private ConsultasCartaoClient consultaClient;

	@Autowired
	private CarteiraRepository carteiraRepository;

	@PostMapping("/api/cartoes/{id}/carteiras")
	@Transactional
	public ResponseEntity<?> cadastraAviso(@PathVariable("id") Long id,
			@RequestBody @Valid CarteiraRequest carteiraRequest, UriComponentsBuilder uriBuilder) {

		Optional<Cartao> cartao = cartaoRepository.findById(id);

		if (cartao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartao nao encontrado");
		}

		if (cartao.get().getStatusCartao().equals(StatusCartao.BLOQUEADO)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cartão bloqueado");
		}
		
		Optional<Carteira> possivelCarteira = carteiraRepository.findByTiposCarteiraAndCartao(carteiraRequest.getTiposCarteira(),cartao.get());
		if (possivelCarteira.isPresent() && cartao.isPresent()) {
			return ResponseEntity.badRequest().body("Cartão já cadastrado nesta carteira");
		}

		try {
			
			consultaClient.associaCarteira(cartao.get().getNumero(), carteiraRequest);
			Carteira carteira = carteiraRequest.toModel(cartao.get());
			carteiraRepository.save(carteira);

			URI location = uriBuilder.path("/api/cartao/{id}/carteira/{id}").build(cartao.get().getId(),
					carteira.getId());
			return ResponseEntity.created(location).build();

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível adicionar o cartao");
		}
	}
}
