package br.com.apidigitalfinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.apidigitalfinanceiro.domain.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Integer> {

	@Transactional(readOnly = true)
	Fatura findByStatusAndNaturezamovimentoAndHistoricoCentrocustoId(String status, String naturezamovimento,
			int centrocustoId);

	@Transactional(readOnly = true)
	List<Fatura> findAllByStatusAndNaturezamovimentoAndHistoricoAgregadofinanceirosCentrocustoId(String status,
			String naturezamovimento, int centrocustoId);

	@Transactional(readOnly = true)
	List<Fatura> findAllByStatusAndTipomovimento(String status, String tipomovimento);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) <?1 AND"
			+ "  f.status =?2   AND f.historico.id=?3 And f.tipomovimento=?4  group by f.historico.id")
	double totalAnterior(Integer exercicio, String status, Integer id, String tipomovimento);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) =?1 AND"
			+ "  f.status =?2   AND f.historico.id=?3 And f.tipomovimento=?4  group by f.historico.id")
	double totalexercicioAtual(Integer exercicio, String status, Integer id, String tipomovimento);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) >?1 AND"
			+ "  f.status =?2   AND f.historico.id=?3  And f.tipomovimento=?4  group by f.historico.id")
	double totalexercicioPosterior(Integer exercicio, String status, Integer id, String tipomovimento);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) =?1 AND"
			+ " EXTRACT(MONTH FROM f.dataVencimento) =?2 AND  f.status =?3 And f.tipomovimento=?4  ")
	double totalmesByExercicio(Integer exercicio, Integer mes, String status, String tipomovimento);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) =?1 AND"
			+ " EXTRACT(MONTH FROM f.dataVencimento) =?2 AND  f.status =?3   AND f.historico.id=?4 "
			+ "And f.tipomovimento=?5  group by f.historico.id ")
	double totalmes(Integer exercicio, Integer mes, String status, Integer id, String tipomovimento);

	@Query("SELECT EXTRACT(YEAR FROM f.dataVencimento ) FROM Fatura f WHERE  f.tipomovimento=?1 GROUP BY  EXTRACT(YEAR FROM f.dataVencimento)")
	List<Integer> findbyano(String tipomovimento);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) =?1 AND"
			+ "   f.status =?2 And f.tipomovimento=?3")
	double totalAnoByExercicio(Integer exercicio, String status, String tipoMovimento);

	// Movimento financeiro
	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) <?1 AND"
			+ "  f.status =?2   AND f.movimentoFinanceiro.id=?3 group by f.movimentoFinanceiro.id")
	double totalmovimentoFinanceiroAnterior(Integer exercicio, String status, Integer id);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) =?1 AND"
			+ "  f.status =?2   AND f.movimentoFinanceiro.id=?3  group by f.movimentoFinanceiro.id")
	double totalemovimentoFinanceiroxercicioAtual(Integer exercicio, String status, Integer id);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) >?1 AND"
			+ "  f.status =?2   AND f.movimentoFinanceiro.id=?3  group by f.movimentoFinanceiro.id")
	double totalmovimentoFinanceiroexercicioPosterior(Integer exercicio, String status, Integer id);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) =?1 AND"
			+ " EXTRACT(MONTH FROM f.dataVencimento) =?2 AND  f.status =?3  group by f.movimentoFinanceiro.id")
	double totalmovimentoFinanceiromesByExercicio(Integer exercicio, Integer mes, String status);

	@Query("SELECT  SUM( f.valor+ f.jurus+ f.multa- f.desconto) FROM Fatura f WHERE EXTRACT(YEAR FROM f.dataVencimento) =?1 AND"
			+ " EXTRACT(MONTH FROM f.dataVencimento) =?2 AND  f.status =?3   AND f.movimentoFinanceiro.id=?4  And f.tipomovimento=?5"
			+ "  group by f.movimentoFinanceiro.id ")
	double totalmovimentoFinanceiromes(Integer exercicio, Integer mes, String status, Integer id, String tipoMovimento);

	// DemosntrativoFinanceiro

	@Query(value = "SELECT" + 
	"  (SELECT m.id FROM grupo_financeiro m WHERE m.id = f.historico_id) AS id,"
			+ "  (SELECT m.name FROM grupo_financeiro m WHERE m.id = f.historico_id) AS movimentofinanceiro,"
			+ "  (f.status) AS descricaofatura,"
			+ "  IFNULL((SELECT SUM(valor + jurus + multa - desconto) AS valor FROM fatura where status = 2 and tipomovimento = :tipoMovimento and EXTRACT(YEAR FROM data_vencimento) = :ano AND EXTRACT(MONTH FROM data_vencimento) = :mes and f.historico_id = historico_id ), 0) AS valorrealizar,"
			+ "  IFNULL((SELECT SUM(valor + jurus + multa - desconto) AS valor FROM fatura where status = 3 and tipomovimento = :tipoMovimento and EXTRACT(YEAR FROM data_quitacao) = :ano AND EXTRACT(MONTH FROM data_quitacao) = :mes and f.historico_id = historico_id ), 0) AS valorrealizado"
			+ " FROM  fatura f" + " WHERE " + "  f.tipomovimento = :tipoMovimento AND"
			+ " ( EXTRACT(YEAR FROM f.data_vencimento) = :ano or EXTRACT(YEAR FROM f.data_quitacao) = :ano) AND  "
			+ " ( EXTRACT(MONTH FROM f.data_vencimento) = :mes or  EXTRACT(MONTH FROM f.data_quitacao) = :mes)  " 			 
			+ " group by f.historico_id,f.status  ", nativeQuery = true)
	List<Object[]> faturasdemosntrativofinanceiro(@Param("tipoMovimento") int tipoMovimento, @Param("ano") int ano,
			@Param("mes") int mes);

	@Query(value = " SELECT "

			+ " (select m.id from grupo_financeiro m where m.id=f.historico_id  )  AS id, "
			+ " (select m.name from grupo_financeiro m where m.id=f.historico_id   ) AS movimentofinanceiro, "
			+ "  (SELECT   name  from fatura where id= f.id) AS descricaofatura,"
			+ "  IFNULL((SELECT SUM(valor + jurus + multa - desconto) AS valor FROM fatura where status = 2 and tipomovimento <>2 and EXTRACT(YEAR FROM data_vencimento) = :ano AND EXTRACT(MONTH FROM data_vencimento) = :mes  and id= f.id ), 0) AS valorrealizar,"
			+ "  IFNULL((SELECT SUM(valor + jurus + multa - desconto) AS valor FROM fatura where status = 2 and tipomovimento = 2 and EXTRACT(YEAR FROM data_vencimento) = :ano AND EXTRACT(MONTH FROM data_vencimento) = :mes  and id= f.id ), 0) AS valorrealizado,"
			+ "  f.data_vencimento as data " + "  FROM   fatura f " + "  WHERE f.status=2 and "
			+ "  EXTRACT(YEAR FROM f.data_vencimento) =:ano AND  " + "  EXTRACT(MONTH FROM f.data_vencimento) = :mes "
			+ "  GROUP BY  f.id,f.historico_id  ", nativeQuery = true)
	List<Object[]> faturasItensdemosntrativofinanceiro(@Param("ano") int ano, @Param("mes") int mes);

	@Query(value = "SELECT YEAR(f.data_movimento) AS exercicio FROM fatura f GROUP BY YEAR(f.data_movimento)", nativeQuery = true)
	List<Object[]> faturasExercicioSomentedemosntrativofinanceiro();
	
	@Query(value = " SELECT  "
			+ "  ifnull((SELECT sum(valor + jurus + multa - desconto) AS FIELD_1 FROM fatura WHERE status = 2 and tipomovimento = 2 and EXTRACT(YEAR FROM data_vencimento) = :ano AND EXTRACT(MONTH FROM data_vencimento) = :mes), 0) AS valoresSaidaAberto, "
			+ "  IFNULL((SELECT sum(valor + jurus + multa - desconto) AS FIELD_1 FROM fatura WHERE status = 3 and tipomovimento = 2 and EXTRACT(YEAR FROM data_quitacao) = :ano AND EXTRACT(MONTH FROM data_quitacao) = :mes), 0) AS valoresSaidaQuit, "
			+ "  ifnull((SELECT sum(valor + jurus + multa - desconto) AS FIELD_1 FROM fatura WHERE status = 2 and tipomovimento = 1 and EXTRACT(YEAR FROM data_vencimento) = :ano AND EXTRACT(MONTH FROM data_vencimento) = :mes), 0) AS valoresEntradaAberto, "
			+ "  IFNULL((SELECT sum(valor + jurus + multa - desconto) AS FIELD_1 FROM fatura WHERE status = 3 and tipomovimento = 1 and EXTRACT(YEAR FROM data_quitacao) = :ano AND EXTRACT(MONTH FROM data_quitacao) = :mes), 0) AS valoresEntradaSaidaQuit "
			 , nativeQuery = true)
	List<Object[]> faturasSomenteItensdemosntrativofinanceiro(@Param("ano") int ano, @Param("mes") int mes);

}
