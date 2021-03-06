package br.com.apidigitalfinanceiro.domain.intefaces;

import java.util.List;

import br.com.apidigitalfinanceiro.domain.ContacFuncionarios;
import br.com.apidigitalfinanceiro.domain.Empresas;

public interface PessoaBaseInterface<BC extends BaseContactInterface,BE extends BaseEnderecoInterface> {

	public Integer getId();
	
	public void setId(Integer id);

	public String getName();

	public String getDescricao();

	public String getCpfOnCnpj();

	public String getNamewar();
	
	public String getAvatar();
	
	public String getAvatarView();

	public String getEmail();

	public BC getContatoPrincipal();
	
	public BE getEnderecoPrincipal();
	
	public void setEnderecos(List<BE> enderecos) ;
	
	public void setEmpresa(Empresas empresa);
	
	public List<BC> getContatos();

}
