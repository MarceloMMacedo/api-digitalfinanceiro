package br.com.apidigitalfinanceiro.controllers;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.apidigitalfinanceiro.domain.Parceiros;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.services.ParceirosService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;

@RestController
@RequestMapping(value = "/parceiros")
public class ParceirosController extends PessoaBaseController<Parceiros> {

	private static final long serialVersionUID = 1L;

	@Autowired
	ParceirosService ParceirosService;

	 @Override
	public ServiceImpl<Parceiros> service() {
		// TODO Auto-generated method stub
		return ParceirosService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	public ResponseEntity<Integer> insert(Parceiros objDto) {
		objDto = ParceirosService.insert(objDto);
		objDto.setStatus(StatusActiv.ATIVO.getDescricao());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objDto.getId()).toUri();
		return ResponseEntity.created(uri).body(objDto.getId());
	}
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}/picture", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) throws IOException {
		String s = ParceirosService.uploadProfilePicture(file, id, "avatar");
		return ResponseEntity.ok().body(s);

	}

}
