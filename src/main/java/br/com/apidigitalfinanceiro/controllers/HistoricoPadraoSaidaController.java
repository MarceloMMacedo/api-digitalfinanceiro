package br.com.apidigitalfinanceiro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalfinanceiro.domain.HistoricoPadraoSaida;
import br.com.apidigitalfinanceiro.services.HistoricoPadraoSaidaService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;

@RestController
@RequestMapping(value = "/historicopadraosaidas")
public class HistoricoPadraoSaidaController extends ControllerImp<HistoricoPadraoSaida> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	HistoricoPadraoSaidaService service;

	@Override
	public ServiceImpl<HistoricoPadraoSaida> service() {
		return service;
	}
}
