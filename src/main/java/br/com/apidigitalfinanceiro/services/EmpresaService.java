package br.com.apidigitalfinanceiro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.ContacEmpresas;
import br.com.apidigitalfinanceiro.domain.Empresas;
import br.com.apidigitalfinanceiro.domain.EnderecoEmpresas;
import br.com.apidigitalfinanceiro.repository.ContactEmpreRepository;
import br.com.apidigitalfinanceiro.repository.EmpresasRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoEmpreRepository;

@Service
public class EmpresaService extends PessoaBaseService<Empresas, ContacEmpresas, EnderecoEmpresas, Empresas> {

	private static final long serialVersionUID = 1L;

	@Autowired
	EmpresasRepository repo;

	@Autowired
	EnderecoEmpreRepository enderecoEmpreRepository;

	@Autowired
	ContactEmpreRepository contactEmpreRepository;

	@Override
	public JpaRepository<Empresas, Integer> repo() {
		return repo;
	}

	@Override
	public JpaRepository<ContacEmpresas, Integer> getRepoContact() {
		// TODO Auto-generated method stub
		return contactEmpreRepository;
	}

	@Override
	public JpaRepository<EnderecoEmpresas, Integer> getRepoEnd() {
		// TODO Auto-generated method stub
		return enderecoEmpreRepository;
	}

	@Override
	public JpaRepository<Empresas, Integer> getRepo() {
		return repo;
	}

}
