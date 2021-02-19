package br.com.zup.propostas.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zup.propostas.proposta.Proposta;

@FeignClient(name="cartaoClient", url="${service.cartao.url}")
public interface CartaoClient {

	@GetMapping
	ConsultaCartaoResponse consultaCartao(@RequestParam("idProposta") String idProposta);
	
	class ConsultaCartaoResponse{
		
		private String id;
		
		private String titular;
		
		private LocalDateTime emitidoEm;
		
		private BigDecimal limite;
		
		private Long idProposta;
		
		public ConsultaCartaoResponse(String id, String titular, LocalDateTime emitidoEm, BigDecimal limite,
				Long idProposta) {
			this.id = id;
			this.titular = titular;
			this.emitidoEm = emitidoEm;
			this.limite = limite;
			this.idProposta = idProposta;
		}

		public String getId() {
			return id;
		}
		
		public Long getIdProposta() {
			return idProposta;
		}
		
		public Cartao toModel(Proposta proposta) {
	        return new Cartao(this.id, this.titular,this.emitidoEm,  this.limite, proposta);
	    }
	}
}
