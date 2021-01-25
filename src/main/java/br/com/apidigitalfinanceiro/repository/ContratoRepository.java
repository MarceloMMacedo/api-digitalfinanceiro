package br.com.apidigitalfinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
	List<Contrato> findByStatus(String status);
}
