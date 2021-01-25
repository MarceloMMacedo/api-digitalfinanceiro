package br.com.apidigitalfinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.apidigitalfinanceiro.domain.MovimentoOrdemServico;

public interface MovimentoOrdemServicoRepository extends JpaRepository<MovimentoOrdemServico, Integer> {

	@Transactional(readOnly = true)
	List<MovimentoOrdemServico> findAllByStatus(String status);

	@Transactional(readOnly = true)
	List<MovimentoOrdemServico> findAllByTipomovimentoAndStatus(String tipomovimento, String status);

	@Transactional(readOnly = true)
	MovimentoOrdemServico findByContratoIdAndStatus(Integer contrato, String status);
	
	@Transactional(readOnly = true)
	@Query( "SELECT c.id AS id, c.name AS historico  FROM MovimentoOrdemServico AS c where c.status=?1 GROUP BY c.id ")
	List<Object[]> movimentosByFaturas(String status);
	
	 
	 
}
