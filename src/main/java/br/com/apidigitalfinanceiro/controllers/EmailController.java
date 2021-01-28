package br.com.apidigitalfinanceiro.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.apidigitalfinanceiro.services.EmailCenterServices;

@Controller
@RequestMapping(value = "/emails")
public class EmailController implements Serializable {

 
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EmailCenterServices emailService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )") 
	@GetMapping(value = "/centrocusto" )
	public ResponseEntity<Integer>   centrocusto() { 
		emailService.centrocusto();
		return ResponseEntity.ok().body(200 );
	}

	
}
