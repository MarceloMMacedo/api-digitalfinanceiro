package br.com.apidigitalfinanceiro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.apidigitalfinanceiro.domain.ContasBanco;
import br.com.apidigitalfinanceiro.services.ContasBancoService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;

@Controller
@RequestMapping(value = "/contasbanco")
public class ContasBancoController extends ControllerImp<ContasBanco> {

	private static final long serialVersionUID = 1L;
	@Autowired
	ContasBancoService service;

	@Override
	public ServiceImpl<ContasBanco> service() {
		return service;
	}
}
