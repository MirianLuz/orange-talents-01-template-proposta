package br.com.zup.propostas.biometria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BiometriaResponseController {

	@Autowired
	private BiometriaRepository repository;
	
	@GetMapping(value = "/cartoes/{id}/biometria/{idBiometria")
	public ResponseEntity<?> consultaBiometria(@PathVariable Long id, @PathVariable("idBiometria") Long idBiometria) {
		Biometria biometria = repository.getOne(id);
		if (biometria == null) {
			return ResponseEntity.notFound().build();
		}
		
		BiometriaResponse biometriaResponse = new BiometriaResponse(biometria);
		return ResponseEntity.ok(biometriaResponse);
		
	}
}
