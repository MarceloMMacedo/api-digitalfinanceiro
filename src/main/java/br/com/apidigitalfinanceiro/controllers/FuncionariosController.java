package br.com.apidigitalfinanceiro.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.apidigitalfinanceiro.domain.Funcionarios;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.services.FuncionariosService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionariosController extends PessoaBaseController<Funcionarios> {

	private static final long serialVersionUID = 1L;

	@Autowired
	FuncionariosService funcionariosService;

	 @Override
	public ServiceImpl<Funcionarios> service() {
		// TODO Auto-generated method stub
		return funcionariosService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	public ResponseEntity<Integer> insert(Funcionarios objDto) {
		objDto = funcionariosService.insert(objDto);
		objDto.setStatus(StatusActiv.ATIVO.getDescricao());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objDto.getId()).toUri();
		return ResponseEntity.created(uri).body(objDto.getId());
	}

	/*@Override
	@RequestMapping(value = "/baseall", method = RequestMethod.GET)
	public List<BaseDto<Empresas>> findBaseAll() {
		// TODO Auto-generated method stub
		return service().findBaseAll();
	}*/
}
