package br.com.zup.propostas.carteiras;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class CarteiraController {
	
	Logger logger = LoggerFactory.getLogger(CarteiraController.class);

	private final CartaoRepository cartaoRepository;

	private final ConsultasCartaoClient consultaClient;

	private final CarteiraRepository carteiraRepository;
	
	private final Tracer tracer;
	
	public CarteiraController(CartaoRepository cartaoRepository, ConsultasCartaoClient consultaClient,
			CarteiraRepository carteiraRepository, Tracer tracer) {
		this.cartaoRepository = cartaoRepository;
		this.consultaClient = consultaClient;
		this.carteiraRepository = carteiraRepository;
		this.tracer = tracer;
	}


	@PostMapping("/api/cartoes/{id}/carteiras")
	@Transactional
	public ResponseEntity<?> cadastraCarteira(@PathVariable("id") Long id,
										   @RequestBody @Valid CarteiraRequest carteiraRequest, 
										   UriComponentsBuilder uriBuilder) {
		
		Span activeSpan = tracer.activeSpan();
        //TAGS
        activeSpan.setTag("idCartao", id);
        //BAGGAGE
        activeSpan.setBaggageItem("traceID", "0001");
        //LOG
        activeSpan.log("associandoCarteira");

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
			logger.info("INFORMAR CARTEIRA AO SISTEMA LEGADO");
			consultaClient.associaCarteira(cartao.get().getNumero(), carteiraRequest);
			Carteira carteira = carteiraRequest.toModel(cartao.get());
			carteiraRepository.save(carteira);
			logger.info("CARTEIRA ASSOCIADA E SALVA NO BANCO");
			URI location = uriBuilder.path("/api/cartao/{id}/carteira/{id}").build(cartao.get().getId(),
					carteira.getId());
			return ResponseEntity.created(location).build();

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível adicionar o cartao");
		}
	}
}
