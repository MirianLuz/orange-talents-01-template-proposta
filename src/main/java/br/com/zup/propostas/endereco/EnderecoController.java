package br.com.zup.propostas.endereco;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@PostMapping
	public ResponseEntity<?> cadastraEndereco(@RequestBody @Valid EnderecoRequest request) {
		Endereco endereco = request.toModel();
		enderecoRepository.save(endereco);
				
		return ResponseEntity.ok().build();
	}
}
