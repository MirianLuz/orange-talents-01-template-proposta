package br.com.zup.propostas.proposta;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.propostas.endereco.EnderecoRepository;


@RestController
@RequestMapping("/propostas")
public class PropostaController {

	@Autowired
	private PropostaRepository propostaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@PostMapping
	public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder){
		Proposta proposta = request.toModel(enderecoRepository);
		propostaRepository.save(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
