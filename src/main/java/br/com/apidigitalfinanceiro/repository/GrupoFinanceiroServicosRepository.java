package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroServicos;

@Repository
public interface GrupoFinanceiroServicosRepository extends JpaRepository<GrupoFinanceiroServicos, Integer> {

}
