package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroAnuncio;

@Repository
public interface GrupoFinanceiroAnuncioRepository extends JpaRepository<GrupoFinanceiroAnuncio, Integer> {

}
