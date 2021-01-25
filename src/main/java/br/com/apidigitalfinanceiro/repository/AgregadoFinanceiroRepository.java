package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.AgregadoFinanceiro;

@Repository
public interface AgregadoFinanceiroRepository extends JpaRepository<AgregadoFinanceiro, Integer> {

}
