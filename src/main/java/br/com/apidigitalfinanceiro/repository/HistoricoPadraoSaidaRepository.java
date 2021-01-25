package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.HistoricoPadraoSaida;

@Repository
public interface HistoricoPadraoSaidaRepository extends JpaRepository<HistoricoPadraoSaida, Integer> {

}
