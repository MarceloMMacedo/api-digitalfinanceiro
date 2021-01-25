package br.com.apidigitalfinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.apidigitalfinanceiro.domain.MovimentoSaida;

public interface MovimentoSaidaRepository extends JpaRepository<MovimentoSaida, Integer> {
	@Transactional(readOnly = true)
	List<MovimentoSaida> findAllByStatus(String status);
	
	@Transactional(readOnly = true)
	@Query( "SELECT c.historico.id AS id, c.historico.name AS historico  FROM MovimentoSaida AS c where c.status=?1 GROUP BY c.historico.id ")
	List<Object[]> movimentosByFaturas(String status);
	
	@Transactional(readOnly = true)
	@Query( "SELECT c.id AS id, c.name AS historico  FROM MovimentoSaida AS c where c.status=?1 and c.historico.id=?2  GROUP BY c.id ")
	List<Object[]> movimentosByFaturasAndHistorico(String status,Integer id);
	 
	
}
