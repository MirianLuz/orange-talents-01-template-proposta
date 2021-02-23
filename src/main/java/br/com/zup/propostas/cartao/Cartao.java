package br.com.zup.propostas.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.propostas.Aviso.Aviso;
import br.com.zup.propostas.bloqueio.BloqueioCartao;
import br.com.zup.propostas.carteiras.Carteira;
import br.com.zup.propostas.proposta.Proposta;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String numero;
	
	@NotBlank
	private String titular;
	
	private LocalDateTime emitidoEm;
	
	private BigDecimal limite;
	
	@OneToOne
	@NotNull
	private Proposta proposta;
	
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<BloqueioCartao> bloqueios;

	@Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;
	
	@OneToMany(mappedBy = "cartao", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Aviso> avisos;
	
	@OneToMany(mappedBy = "cartao",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Carteira> carteira;
	
	@Deprecated
	public Cartao() {
	}
	
	public Cartao(@NotBlank String numero, @NotBlank String titular, LocalDateTime emitidoEm, BigDecimal limite,
			@NotNull Proposta proposta) {
		this.numero = numero;
		this.titular = titular;
		this.emitidoEm = emitidoEm;
		this.limite = limite;
		this.proposta = proposta;
		this.statusCartao = StatusCartao.DESBLOQUEADO;
	}
	
	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public String getTitular() {
		return titular;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public StatusCartao getStatusCartao() {
		return statusCartao;
	}
	
	public List<Carteira> getCarteira() {
		return carteira;
	}

	public void vincularBloqueio(BloqueioCartao bloqueio) {
        this.bloqueios.add(bloqueio);
    }

	public void atualizaStatusCartao(StatusCartao statusCartao) {
		this.statusCartao = statusCartao;
	}

	public void adicionarAviso(Aviso aviso, CartaoRepository cartaoRepository) {
		this.avisos.add(aviso);
		cartaoRepository.save(this);
	}

}
