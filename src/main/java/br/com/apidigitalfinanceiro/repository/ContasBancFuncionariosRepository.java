package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apidigitalfinanceiro.domain.ContasBancFuncionarios;

public interface ContasBancFuncionariosRepository extends JpaRepository<ContasBancFuncionarios, Integer> {

}
