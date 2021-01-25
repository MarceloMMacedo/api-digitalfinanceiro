package br.com.apidigitalfinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.apidigitalfinanceiro.domain.Funcionarios;

@Repository
public interface FuncionariosRepository extends JpaRepository<Funcionarios, Integer> {
 
	@Transactional(readOnly=true)
	Funcionarios findByEmail(String email);

	@Transactional(readOnly=true)
	Funcionarios findByEmailAndStatus(String email, String status);
	
	@Transactional(readOnly=true)
	List<Funcionarios> findAllByEmpresaId(Integer id);
}
