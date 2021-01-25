package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroContrato;

@Repository
public interface GrupoFinanceiroContratoRepository extends JpaRepository<GrupoFinanceiroContrato, Integer> {

}
