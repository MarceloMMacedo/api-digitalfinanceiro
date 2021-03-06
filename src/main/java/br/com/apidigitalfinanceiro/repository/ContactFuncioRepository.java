package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.ContacFuncionarios;

@Repository
public interface ContactFuncioRepository extends JpaRepository<ContacFuncionarios, Integer> {

}
