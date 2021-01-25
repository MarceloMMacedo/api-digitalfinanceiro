package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.ContacEmpresas;

@Repository
public interface ContactEmpreRepository extends JpaRepository<ContacEmpresas, Integer> {

}
