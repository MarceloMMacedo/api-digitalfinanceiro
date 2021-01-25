package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.ContacFornecedores;

@Repository
public interface ContactFornecedoresRepository extends JpaRepository<ContacFornecedores, Integer> {

}
