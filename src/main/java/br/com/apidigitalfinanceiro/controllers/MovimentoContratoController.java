package br.com.apidigitalfinanceiro.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.apidigitalfinanceiro.domain.MovimentoContrato;
import br.com.apidigitalfinanceiro.dto.Events;
import br.com.apidigitalfinanceiro.dto.FaturaContratoDto;
import br.com.apidigitalfinanceiro.dto.ResumoContasMovimento;
import br.com.apidigitalfinanceiro.services.MovimentoContratoServices;
import br.com.apidigitalfinanceiro.services.ServiceImpl;

@RestController
@RequestMapping(value = "/movimentocontrato")
public class MovimentoContratoController extends ControllerImp<MovimentoContrato> {

	private static final long serialVersionUID = 1L;

	@Autowired
	MovimentoContratoServices service;

	@Override
	public ServiceImpl<MovimentoContrato> service() {
		return service;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/resumocontasrecebercontrato", method = RequestMethod.GET)
	public ResponseEntity<List<ResumoContasMovimento>> resumocontasrecebercontrato(
			@RequestParam(value = "value", defaultValue = "0") Integer exercicio) {

		return ResponseEntity.ok().body(service.resumoContasRecebers(exercicio));
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/exercicios", method = RequestMethod.GET)
	public ResponseEntity<List<Integer>> exerciciosAbert() {

		return ResponseEntity.ok().body(service.exerciciosAbert());
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/totalrecebercontrator", method = RequestMethod.GET)
	public ResponseEntity<Double> totalrecebercontrator() {
		return ResponseEntity.ok().body(service.totalReceber());
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/resumocontasrecebercontratoexercico", method = RequestMethod.GET)
	public ResponseEntity<List<ResumoContasMovimento>> resumocontasrecebercontratoexercico() {
		return ResponseEntity.ok().body(service.resumocontasrecebersexercico());
	}
 
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public ResponseEntity<List<Events>> events() {
		return ResponseEntity.ok().body(service.createEvent());
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/quitarfatura", method = RequestMethod.PUT)
	public ResponseEntity<Void> quitarfatura(@Validated @RequestBody FaturaContratoDto objDto)   {
	service.quitarFatura(objDto);

		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}/quitarfatura", method = RequestMethod.GET)
	public ResponseEntity<FaturaContratoDto> quitarfatura( @PathVariable Integer id) {
		 

		return ResponseEntity.ok(service.faturaContratoDto(id)) ;
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}/picture", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) throws IOException {
		String s = service.uploadProfilePicture(file, id, "imagemfatura");
		return ResponseEntity.ok().body(s);


	}

	 
}
