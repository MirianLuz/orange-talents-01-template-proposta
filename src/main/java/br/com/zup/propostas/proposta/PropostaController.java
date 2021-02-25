package br.com.zup.propostas.proposta;

import java.net.URI;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import feign.FeignException;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

	private final PropostaRepository propostaRepository;

	private final AnaliseClient analiseClient;

	public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient) {
		this.propostaRepository = propostaRepository;
		this.analiseClient = analiseClient;
	}

	@PostMapping
	public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request,
			UriComponentsBuilder uriBuilder) {
		if (propostaRepository.existsByDocumento(request.getDocumento())) {
			HashMap<String, Object> resposta = new HashMap<>();
			resposta.put("mensagem", "Já existe documento cadastrado");
			return ResponseEntity.unprocessableEntity().body(resposta);
		}

		Proposta proposta = request.toModel();
		propostaRepository.save(proposta);

		try {
			AnaliseClient.ConsultaStatusRequest requisicaoDaAnalise = new AnaliseClient.ConsultaStatusRequest(proposta);
			AnaliseClient.ConsultaStatusResponse resposta = analiseClient.consulta(requisicaoDaAnalise);
			proposta.atualizaStatus(resposta.getResultadoSolicitacao());
		} catch (FeignException.UnprocessableEntity ex) {
			proposta.atualizaStatus("COM_RESTRICAO");

		}

		propostaRepository.save(proposta);

		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
