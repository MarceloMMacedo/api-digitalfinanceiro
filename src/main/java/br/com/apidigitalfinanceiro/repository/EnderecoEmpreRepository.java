package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.EnderecoEmpresas;

@Repository
public interface EnderecoEmpreRepository extends JpaRepository<EnderecoEmpresas, Integer> {

}
