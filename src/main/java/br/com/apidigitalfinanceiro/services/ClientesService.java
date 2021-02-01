package br.com.apidigitalfinanceiro.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.config.security.UserSS;
import br.com.apidigitalfinanceiro.config.services.UserService;
import br.com.apidigitalfinanceiro.domain.Clientes;
import br.com.apidigitalfinanceiro.domain.ContacClientes;
import br.com.apidigitalfinanceiro.domain.EnderecoClientes;
import br.com.apidigitalfinanceiro.mail.storage.EmailProperties;
import br.com.apidigitalfinanceiro.repository.ClientesRepository;
import br.com.apidigitalfinanceiro.repository.ContactClientesRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoClientesRepository;

@Service
public class ClientesService
		extends PessoaBaseService<Clientes, ContacClientes, EnderecoClientes, Clientes> {

	private static final long serialVersionUID = 1L;

	@Autowired
	ClientesRepository repo;

	@Autowired
	EnderecoClientesRepository enderecoEmpreRepository;

	@Autowired
	ContactClientesRepository contactEmpreRepository;

	@Override
	public JpaRepository<Clientes, Integer> repo() {
		return repo;
	}

	@Override
	public JpaRepository<Clientes, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public JpaRepository<ContacClientes, Integer> getRepoContact() {
		// TODO Auto-generated method stub
		return contactEmpreRepository;
	}

	@Override
	public JpaRepository<EnderecoClientes, Integer> getRepoEnd() {
		// TODO Auto-generated method stub
		return enderecoEmpreRepository;
	}

	
	@Override
	public void sendemailreport(EmailProperties properties) {
		UserSS user = UserService.authenticated();
		Map<String, Object> parameters = new HashMap<String, Object>();
		properties.setFrom(user.getEmail());
		String templates = "centrocusto";
		htmlEmailService.sendemailreport(properties, templates, parameters, findAll());
	}

}
