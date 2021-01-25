package br.com.apidigitalfinanceiro.domain.intefaces;

import br.com.apidigitalfinanceiro.domain.Empresas;

public interface BaseEntity {

	Integer getId();

	/*
	 * String getName();
	 * 
	 * String getAvatar();
	 * 
	 * String getAvatarView();
	 * 
	 * String getEmail();
	 */
	void setId(Integer id);

	 void setEmpresa(Empresas empresa);

}
