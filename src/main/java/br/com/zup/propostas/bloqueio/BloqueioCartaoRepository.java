package br.com.zup.propostas.bloqueio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueioCartaoRepository extends JpaRepository<BloqueioCartao, Long>{

	

}
