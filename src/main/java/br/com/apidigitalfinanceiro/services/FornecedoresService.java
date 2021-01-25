package br.com.apidigitalfinanceiro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.ContacFornecedores;
import br.com.apidigitalfinanceiro.domain.EnderecoFornecedores;
import br.com.apidigitalfinanceiro.domain.Fornecedores;
import br.com.apidigitalfinanceiro.repository.ContactFornecedoresRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoFornecedoresRepository;
import br.com.apidigitalfinanceiro.repository.FornecedoresRepository;

@Service
public class FornecedoresService
		extends PessoaBaseService<Fornecedores, ContacFornecedores, EnderecoFornecedores, Fornecedores> {

	private static final long serialVersionUID = 1L;

	@Autowired
	FornecedoresRepository repo;

	@Autowired
	EnderecoFornecedoresRepository enderecoEmpreRepository;

	@Autowired
	ContactFornecedoresRepository contactEmpreRepository;

	@Override
	public JpaRepository<Fornecedores, Integer> repo() {
		return repo;
	}

	@Override
	public JpaRepository<Fornecedores, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public JpaRepository<ContacFornecedores, Integer> getRepoContact() {
		// TODO Auto-generated method stub
		return contactEmpreRepository;
	}

	@Override
	public JpaRepository<EnderecoFornecedores, Integer> getRepoEnd() {
		// TODO Auto-generated method stub
		return enderecoEmpreRepository;
	}

}
