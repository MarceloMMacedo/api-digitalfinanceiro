package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.ContasBanco;

@Repository
public interface ContasBancoRepository extends JpaRepository<ContasBanco, Integer> {

}
