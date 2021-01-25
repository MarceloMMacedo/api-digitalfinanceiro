package br.com.apidigitalfinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.apidigitalfinanceiro.domain.Fornecedores;

@Repository
public interface FornecedoresRepository extends JpaRepository<Fornecedores, Integer> {
 
	@Transactional(readOnly=true)
	Fornecedores findByEmail(String email);

	@Transactional(readOnly=true)
	Fornecedores findByEmailAndStatus(String email, String status);
	
	@Transactional(readOnly=true)
	List<Fornecedores> findAllByEmpresaId(Integer id);
}
