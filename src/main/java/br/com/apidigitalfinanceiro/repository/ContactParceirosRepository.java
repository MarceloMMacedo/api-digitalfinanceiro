package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.ContacParceiros;

@Repository
public interface ContactParceirosRepository extends JpaRepository<ContacParceiros, Integer> {

}
