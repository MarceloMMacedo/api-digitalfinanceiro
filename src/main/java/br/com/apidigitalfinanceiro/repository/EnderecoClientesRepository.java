package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.EnderecoClientes;

@Repository
public interface EnderecoClientesRepository extends JpaRepository<EnderecoClientes, Integer> {

 
}
