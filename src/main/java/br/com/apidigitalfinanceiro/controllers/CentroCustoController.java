package br.com.apidigitalfinanceiro.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.apidigitalfinanceiro.domain.CentroCusto;
import br.com.apidigitalfinanceiro.services.CentroCustoService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping(value = "/centrocustos")
public class CentroCustoController extends ControllerImp<CentroCusto> {

	private static final long serialVersionUID = 1L;
	@Autowired
	CentroCustoService service;

	@Override
	public ServiceImpl<CentroCusto> service() {
		return service;
	}
	
 
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/viewpdf",method = RequestMethod.GET)
	public ResponseEntity<byte[]> viewpdf( )
			throws JRException, IOException {
		 
		return   ResponseEntity.ok( service().ViewPdf() );
	}
}
