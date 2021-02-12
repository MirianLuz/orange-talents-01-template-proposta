package br.com.zup.propostas.proposta;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultaPropostaController {
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	@GetMapping("/api/propostas/{id}")
	public ResponseEntity<?> consultaProposta(@PathVariable("id") Long id) {
		Optional<Proposta> proposta = propostaRepository.findById(id);
		if (proposta.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proposta não encontrada");
		}
		
		return ResponseEntity.ok(new PropostaResponse(proposta.get()));
	}
}
