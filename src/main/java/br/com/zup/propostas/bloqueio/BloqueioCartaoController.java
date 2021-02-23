package br.com.zup.propostas.bloqueio;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.propostas.cartao.Cartao;
import br.com.zup.propostas.cartao.CartaoRepository;
import br.com.zup.propostas.cartao.StatusCartao;
import feign.FeignException;

@RestController
@RequestMapping("/api/cartoes/{id}/bloqueio")
public class BloqueioCartaoController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ConsultasCartaoClient consultaBloqueioClient;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastraBloqueio(@PathVariable("id") Long id, HttpServletRequest request){
		
		Optional<Cartao> cartao = cartaoRepository.findById(id);
		
		if (cartao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartao nao encontrado");
		}
		
		String ipAddress = request.getRemoteAddr();
        String userAgentStr = request.getHeader("User-Agent");
        
		
		if(ipAddress.isBlank() || userAgentStr.isBlank()){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Dados da requisição inválidos");
        }
		
		BloqueioCartao bloqueio = new BloqueioCartao(cartao.get(),ipAddress, userAgentStr);
		
		
		try {
			
			consultaBloqueioClient.consultaBloqueio(cartao.get().getNumero(), new BloqueioCartaoRequest("Propostas"));
			
			cartao.get().vincularBloqueio(bloqueio);
			cartao.get().atualizaStatusCartao(StatusCartao.BLOQUEADO);
			cartaoRepository.save(cartao.get());
			
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("O cartão já está bloqueado!");
		}

		
		return ResponseEntity.ok().build();

	}
}