package br.com.apidigitalfinanceiro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.config.security.UserSS;
import br.com.apidigitalfinanceiro.config.services.UserService;
import br.com.apidigitalfinanceiro.domain.ContacFuncionarios;
import br.com.apidigitalfinanceiro.domain.EnderecoFuncionarios;
import br.com.apidigitalfinanceiro.domain.Funcionarios;
import br.com.apidigitalfinanceiro.dto.BaseDto;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.repository.ContactFuncioRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoFuncRepository;
import br.com.apidigitalfinanceiro.repository.FuncionariosRepository;
import br.com.apidigitalfinanceiro.service.security.exceptions.AuthorizationException;

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

	@Override
	public List<ContacFuncionarios> getListContact(int id) {
		// TODO Auto-generated method stub
		return super.getListContact(id);
	}
	
	@Override
	public ContacFuncionarios saveContact(int id, ContacFuncionarios contact) {
		// TODO Auto-generated method stub
		return super.saveContact(id, contact);
	}
	
	 
	public BaseDto findEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		 BaseDto baseDto= new BaseDto( );
		 Funcionarios f= repo.findByEmailAndStatus(user.getEmail(),  StatusActiv.ATIVO.getDescricao() );
		 f=SetImgSingle("avatar", "avatarView", f);
		 baseDto=new BaseDto(f.getId(), f.getName(), f.getCpfOnCnpj(),f.getAvatar(), f.getAvatarView(), 
				 f.getEmail() ); 
		return  baseDto;
	}
}
