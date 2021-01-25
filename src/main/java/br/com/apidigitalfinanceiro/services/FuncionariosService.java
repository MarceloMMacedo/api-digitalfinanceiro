package br.com.apidigitalfinanceiro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.ContacFuncionarios;
import br.com.apidigitalfinanceiro.domain.EnderecoFuncionarios;
import br.com.apidigitalfinanceiro.domain.Funcionarios;
import br.com.apidigitalfinanceiro.repository.ContactFuncioRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoFuncRepository;
import br.com.apidigitalfinanceiro.repository.FuncionariosRepository;

@Service
public class FuncionariosService
		extends PessoaBaseService<Funcionarios, ContacFuncionarios, EnderecoFuncionarios, Funcionarios> {

	private static final long serialVersionUID = 1L;

	@Autowired
	FuncionariosRepository repo;

	@Autowired
	EnderecoFuncRepository enderecoEmpreRepository;

	@Autowired
	ContactFuncioRepository contactEmpreRepository;

	@Override
	public JpaRepository<Funcionarios, Integer> repo() {
		return repo;
	}

	@Override
	public JpaRepository<Funcionarios, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public JpaRepository<ContacFuncionarios, Integer> getRepoContact() {
		// TODO Auto-generated method stub
		return contactEmpreRepository;
	}

	@Override
	public JpaRepository<EnderecoFuncionarios, Integer> getRepoEnd() {
		// TODO Auto-generated method stub
		return enderecoEmpreRepository;
	}

}
