package br.com.apidigitalfinanceiro.services;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.repository.SendEmailFianaceiroRepository;
import lombok.Data;

@Service
@Data
public class SendEmailFianaceiroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SendEmailFianaceiroRepository repo;

	/*
	 * verifica faturas a pagar dois dias antes do vencimento mando e-mail
	 * 
	 * verifica faturas a Receber dois dias antes do vencimento mando e-mail
	 * 
	 * 
	 * verifica faturas a pagar no dias antes do vencimento mando e-mail
	 * 
	 * verifica faturas a Receber no dias antes do vencimento mando e-mail
	 * 
	 * gera relatório de Resumo financeiro nos dias 15 e 28 de cada mês
	 */

	/*
	 * gera relatório de Resumo financeiro nos dias 1 e 15 de cada mês
	 */

}
