package br.com.apidigitalfinanceiro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroContrato;
import br.com.apidigitalfinanceiro.services.GrupoFinanceiroContratoService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;

@Controller
@RequestMapping(value = "/grupocontrato")
public class GrupoFinanceiroContratoController extends ControllerImp<GrupoFinanceiroContrato> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	GrupoFinanceiroContratoService service;
	


	 @Override
	public ServiceImpl<GrupoFinanceiroContrato> service() { 
		return service;
	}
	
	/*@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Validated @RequestBody GrupoFinanceiroContrato objDto, @PathVariable Integer id) {

		GrupoFinanceiroContrato obj = service.update( objDto); // obj.setId(id);
																											// obj =
		return ResponseEntity.noContent().build();
	}
*/
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value="/{id}/deleteagregado", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.deleteAgregado(id);
		return ResponseEntity.noContent().build();
	}
}
