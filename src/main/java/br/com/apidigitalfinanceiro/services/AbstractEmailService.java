package br.com.apidigitalfinanceiro.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.apidigitalfinanceiro.service.security.exceptions.AuthorizationException;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * /@Override public void sendOrderConfirmationEmail(Pedido obj) {
	 * SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
	 * sendEmail(sm); }
	 * 
	 * protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
	 * SimpleMailMessage sm = new SimpleMailMessage();
	 * sm.setTo(obj.getCliente().getEmail()); sm.setFrom(sender);
	 * sm.setSubject("Pedido confirmado! Código: " + obj.getId());
	 * sm.setSentDate(new Date(System.currentTimeMillis()));
	 * sm.setText(obj.toString()); return sm; }
	 * 
	 * @Override public void sendNewPasswordEmail(Cliente cliente, String newPass) {
	 * SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
	 * sendEmail(sm); }
	 * 
	 * protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String
	 * newPass) { SimpleMailMessage sm = new SimpleMailMessage();
	 * sm.setTo(cliente.getEmail()); sm.setFrom(sender);
	 * sm.setSubject("Solicitação de nova senha"); sm.setSentDate(new
	 * Date(System.currentTimeMillis())); sm.setText("Nova senha: " + newPass);
	 * return sm;
	 * 
	 * 
	 * }
	 * 
	 * protected String htmlFromTemplatePedido(Pedido obj){
	 * 
	 * }
	 */
	protected String htmlFromTemplatePedido(int obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/sendemail", context);

	}

	/*@Override
	public void sendHtmlEmail() {
		try {
			MimeMessage mm = prepareHtmlFromTemplatePedido(1);
			sendEmail(mm);
		} catch (Exception e) {
			throw new AuthorizationException("Acesso negado");
		}

	}

	 protected MimeMessage prepareHtmlFromTemplatePedido(int i) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		messageHelper.setTo("ssss@sss");
		messageHelper.setFrom(sender);
		messageHelper.setSubject("Pedido confirmado! Código: ");
		messageHelper.setSentDate(new Date(System.currentTimeMillis()));
		messageHelper.setText(htmlFromTemplatePedido(i), true);
		return mimeMessage;
	}*/

	@Override
	public void sendEmail(MimeMessage msg) {

	}
}
