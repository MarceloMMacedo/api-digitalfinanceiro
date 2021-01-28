package br.com.apidigitalfinanceiro.services;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.mail.mail.HtmlEmailService;
import br.com.apidigitalfinanceiro.mail.mail.JasperReportsService;
import br.com.apidigitalfinanceiro.mail.storage.EmailProperties;

@Service
public class EmailCenterServices implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	HtmlEmailService htmlEmailService;

	@Autowired
	JasperReportsService jasperReportsService;
	@Autowired
	CentroCustoService centroCustoService;
	


	@Value("${spring.mail.username}")
	private String username;

	public void centrocusto() {
		// JasperReportsService jasperReportsService=new JasperReportsService();

		EmailProperties emailProperties = new EmailProperties();
		emailProperties.setHtml(jasperReportsService.generateHtmlReport("centrocusto",
				new HashMap<>(), centroCustoService.findAll()));
		
		emailProperties.getEmailProperties("Centro de Custo", username,
				"marcelo_macedo01@hotmail.com");
		htmlEmailService.sendHtmlEmail(emailProperties);
	}

}
