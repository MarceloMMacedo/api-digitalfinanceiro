package br.com.apidigitalfinanceiro.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.apidigitalfinanceiro.domain.Modelo;
import br.com.apidigitalfinanceiro.services.ModeloService;
import br.com.apidigitalfinanceiro.services.ServiceImpl; 

@RestController
@RequestMapping(value = "/modelos")
public class ModeloController extends ControllerImp<Modelo> {
	private static final long serialVersionUID = 1L;

	@Autowired
	ModeloService service;

	@Override
	public ServiceImpl<Modelo> service() {
		return service;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}/picture", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) throws IOException {
		String s = service().uploadProfilePicture(file, id, "avatar");
		return ResponseEntity.ok(s);
	}

	 

}
