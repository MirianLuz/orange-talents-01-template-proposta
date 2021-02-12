package br.com.zup.propostas.cartao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.propostas.proposta.Proposta;
import br.com.zup.propostas.proposta.PropostaRepository;
import feign.FeignException;

@Component
public class AnaliseCartaoController {

	private static final Logger log = LoggerFactory.getLogger(AnaliseCartaoController.class);

	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private CartaoClient cartaoClient;

	@Scheduled(fixedRate = 10000)
	public void consultaCartaoPropostasElegiveis() {
		List<Proposta> propostas = propostaRepository.findAllElegiveisSemCartao();
		
		for (Proposta proposta : propostas) {
			try {
				CartaoClient.ConsultaCartaoResponse response = cartaoClient.consultaCartao(proposta.getId().toString());
				proposta.associaCartao(response.toModel(proposta));
				propostaRepository.save(proposta);
			} catch (FeignException ex) {
				log.info("Não encontrou um cartão para a proposta: " + proposta.getId());
			}
		}
	}
}