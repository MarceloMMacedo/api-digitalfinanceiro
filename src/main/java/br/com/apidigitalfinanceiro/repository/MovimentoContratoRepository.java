package br.com.apidigitalfinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.apidigitalfinanceiro.domain.MovimentoContrato;

public interface MovimentoContratoRepository extends JpaRepository<MovimentoContrato, Integer> {

	@Transactional(readOnly = true)
	List<MovimentoContrato> findAllByStatus(String status);
	
	@Transactional(readOnly = true)
	 MovimentoContrato  findByContratoIdAndStatus(Integer id,String status);

	@Transactional(readOnly = true)
	List<MovimentoContrato> findAllByTipomovimentoAndStatus(String tipomovimento, String status);

	 
	@Transactional(readOnly = true)
	@Query( "SELECT c.id AS id, c.name AS historico    FROM MovimentoContrato AS c where c.status=?1 GROUP BY c.id ")
	List<Object[]> movimentosByFaturas(String status);
	
	@Transactional(readOnly = true)
	@Query( "SELECT c.id AS id, c.name AS historico  FROM MovimentoContrato AS c where c.status=?1 and c.historico.id=?2  GROUP BY c.id ")
	List<Object[]> movimentosByFaturasAndHistorico(String status,Integer id);
	 
}
