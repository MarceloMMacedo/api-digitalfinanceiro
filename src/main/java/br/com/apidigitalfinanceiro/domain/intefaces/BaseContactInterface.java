package br.com.apidigitalfinanceiro.domain.intefaces;

public interface BaseContactInterface {  
	public void setId(Integer id);
	public void setPessoas(BaseEntity pessoas);
	public PessoaBaseInterface getPessoas();
}
