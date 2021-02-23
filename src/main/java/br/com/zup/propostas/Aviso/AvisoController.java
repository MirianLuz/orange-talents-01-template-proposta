package br.com.zup.propostas.Aviso;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.propostas.bloqueio.ConsultasCartaoClient;
import br.com.zup.propostas.cartao.Cartao;
import br.com.zup.propostas.cartao.CartaoRepository;
import br.com.zup.propostas.cartao.StatusCartao;
import feign.FeignException;

@RestController
public class AvisoController {
	
	private final Logger logger = LoggerFactory.getLogger(AvisoController.class);
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ConsultasCartaoClient consultaClient;

	@PostMapping("/api/cartoes/{id}/avisos")
	@Transactional
	public ResponseEntity<?> cadastraAviso(@PathVariable("id") Long id, 
										   @RequestBody @Valid AvisoCartaoRequest avisoCartaoRequest, 
										   HttpServletRequest request){
		
		Optional<Cartao> cartao = cartaoRepository.findById(id);
		
		if (cartao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartao nao encontrado");
		}
		
		String ipAddress = request.getRemoteAddr();
        String userAgentStr = request.getHeader("User-Agent");
        
		
		if(ipAddress.isBlank() || userAgentStr.isBlank()){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Dados da requisição inválidos");
        }
		
		if(cartao.get().getStatusCartao().equals(StatusCartao.BLOQUEADO)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cartão bloqueado");
        }
		
		try {
			logger.error("gerando aviso");
			consultaClient.avisos(cartao.get().getNumero(), avisoCartaoRequest);
			Aviso aviso = avisoCartaoRequest.toModel(avisoCartaoRequest, ipAddress, userAgentStr, cartao.get());
			cartao.get().adicionarAviso(aviso, cartaoRepository);
			
		} catch (FeignException e) {
			logger.error("gerando erro");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível adicionar o aviso");
		}
	
		return ResponseEntity.ok().build();
	}
}
