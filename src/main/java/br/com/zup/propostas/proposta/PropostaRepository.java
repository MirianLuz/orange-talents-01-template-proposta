package br.com.zup.propostas.proposta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long>{

	boolean existsByDocumento(String documento);

	@Query("select p from Proposta p LEFT JOIN p.cartao c where p.status = 'ELEGIVEL' and c is null")
	List<Proposta> findAllElegiveisSemCartao();

}
