package br.com.apidigitalfinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalfinanceiro.domain.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo,Integer> {

}
