package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.ContacClientes;

@Repository
public interface ContactClientesRepository extends JpaRepository<ContacClientes, Integer> {

}
