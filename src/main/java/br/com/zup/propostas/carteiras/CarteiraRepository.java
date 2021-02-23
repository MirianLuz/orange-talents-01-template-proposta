package br.com.zup.propostas.carteiras;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.propostas.cartao.Cartao;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long>{

	Optional<Carteira> findByTiposCarteiraAndCartao(TiposCarteira tiposCarteira, Cartao cartao);

}
