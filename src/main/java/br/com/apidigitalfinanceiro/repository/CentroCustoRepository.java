package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.CentroCusto;

@Repository
public interface CentroCustoRepository extends JpaRepository<CentroCusto, Integer> {

}
