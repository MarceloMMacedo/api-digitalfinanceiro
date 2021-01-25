package br.com.apidigitalfinanceiro.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apidigitalfinanceiro.domain.SendEmailFianaceiro;

public interface SendEmailFianaceiroRepository extends JpaRepository<SendEmailFianaceiro, Long> {

	SendEmailFianaceiro findByStatusAndDatalancamentoAndTipoemailreport(String status, Date datalancamento,
			String ipoemailreport);

}
