package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.EnderecoFornecedores;

@Repository
public interface EnderecoFornecedoresRepository extends JpaRepository<EnderecoFornecedores, Integer> {

 
}
