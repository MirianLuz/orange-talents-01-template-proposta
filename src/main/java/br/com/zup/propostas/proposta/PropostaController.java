package br.com.zup.propostas.proposta;

import java.net.URI;
import java.util.HashMap;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

	@Autowired
	private PropostaRepository propostaRepository;

	private final AnaliseClient analiseClient;

	public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient) {
		this.propostaRepository = propostaRepository;
		this.analiseClient = analiseClient;
	}

	@PostMapping
	public ResponseEntity<?> criarProposta(@RequestBody @Valid PropostaRequest request,
			UriComponentsBuilder uriBuilder) {
		
		logger.error("gerando aviso1");
		if (propostaRepository.existsByDocumento(request.getDocumento())) {
			HashMap<String, Object> resposta = new HashMap<>();
			resposta.put("mensagem", "Já existe documento cadastrado");
			return ResponseEntity.unprocessableEntity().body(resposta);
		}
		logger.error("gerando aviso2");
		Proposta proposta = request.toModel();
		propostaRepository.save(proposta);

		try {
			logger.error("gerando aviso3");
			AnaliseClient.ConsultaStatusRequest requisicaoDaAnalise = new AnaliseClient.ConsultaStatusRequest(proposta);
			AnaliseClient.ConsultaStatusResponse resposta = analiseClient.consulta(requisicaoDaAnalise);
			proposta.atualizaStatus(resposta.getResultadoSolicitacao());
		} catch (FeignException.UnprocessableEntity ex) {
			logger.error("gerando aviso4");
			proposta.atualizaStatus("COM_RESTRICAO");

		}
		logger.error("gerando aviso5");
		propostaRepository.save(proposta);

		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
