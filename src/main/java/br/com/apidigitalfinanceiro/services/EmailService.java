package br.com.apidigitalfinanceiro.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.*;
import org.springframework.mail.javamail.*;

public interface EmailService {

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
	void sendEmailReceberContratoCincodias();

	void sendEmailReceberServicosCincodias();

	void sendEmailPagarCincodias();

	void sendEmailReceberContratoVencimento();

	void sendEmailReceberServicosVencimento();

	void sendEmailPagarVencimento();

	void sendEmailDemonstrativoMeioMes();

	void sendEmailDemonstrativoFinalMes();

	void sendEmail(SimpleMailMessage msg);
	
	void sendHtmlEmail();

	void sendEmail(MimeMessage msg);
}
