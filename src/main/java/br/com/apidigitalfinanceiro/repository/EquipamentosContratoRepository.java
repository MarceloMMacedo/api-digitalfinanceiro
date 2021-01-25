package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.EquipamentosContrato;

@Repository
public interface EquipamentosContratoRepository extends JpaRepository<EquipamentosContrato, Integer> {

}
