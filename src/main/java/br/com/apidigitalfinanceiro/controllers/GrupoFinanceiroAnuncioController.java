package br.com.apidigitalfinanceiro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroAnuncio;
import br.com.apidigitalfinanceiro.services.GrupoFinanceiroAnuncioService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;

@Controller
@RequestMapping(value = "/grupoanuncio")
public class GrupoFinanceiroAnuncioController extends ControllerImp<GrupoFinanceiroAnuncio> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	GrupoFinanceiroAnuncioService service;
	


	 @Override
	public ServiceImpl<GrupoFinanceiroAnuncio> service() { 
		return service;
	}
	 
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value="/{id}/deleteagregado", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.deleteAgregado(id);
		return ResponseEntity.noContent().build();
	}
}
