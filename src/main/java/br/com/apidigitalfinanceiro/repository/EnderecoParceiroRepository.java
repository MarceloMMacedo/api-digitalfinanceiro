package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.EnderecoParceiros;

@Repository
public interface EnderecoParceiroRepository extends JpaRepository<EnderecoParceiros, Integer> {

 
}
