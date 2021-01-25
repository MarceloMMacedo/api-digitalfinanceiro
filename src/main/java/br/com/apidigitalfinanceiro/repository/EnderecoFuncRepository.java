package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.EnderecoFuncionarios;

@Repository
public interface EnderecoFuncRepository extends JpaRepository<EnderecoFuncionarios, Integer> {

 
}
