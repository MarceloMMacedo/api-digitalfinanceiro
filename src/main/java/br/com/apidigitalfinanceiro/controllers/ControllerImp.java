package br.com.apidigitalfinanceiro.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.apidigitalfinanceiro.domain.intefaces.BaseEntity;
import br.com.apidigitalfinanceiro.dto.BaseDto;
import br.com.apidigitalfinanceiro.enuns.FuncaoEnum;
import br.com.apidigitalfinanceiro.enuns.Perfil;
import br.com.apidigitalfinanceiro.mail.storage.EmailProperties;
import br.com.apidigitalfinanceiro.services.ServiceImpl;
import br.com.apidigitalfinanceiro.utils.FilesService;
import net.sf.jasperreports.engine.JRException;

public class ControllerImp<T extends BaseEntity> implements ControllerInterfaces<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	FilesService filesService;

	public ServiceImpl<T> service() {
		return null;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<T>> findAll() {
		List<T> list = service().findAll();
		return ResponseEntity.ok().body(list);
	}

	@Override
	public Class<T> getClasse() {
		Class<T> classe = null;
		try {
			Class<T> class1 = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];

			classe = class1;
		} catch (Exception e) {
		}
		return classe;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<T> find(@PathVariable Integer id) {
		T obj = service().find(id);
		return ResponseEntity.ok().body(obj);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Integer> insert(@Validated @RequestBody T objDto) {
		objDto.setId(null);
		objDto = service().insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objDto.getId()).toUri();
		return ResponseEntity.created(uri).body(objDto.getId());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Validated @RequestBody T objDto, @PathVariable Integer id) {

		T obj = service().update(objDto); // obj.setId(id);
											// obj =
		return ResponseEntity.noContent().build();
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	public ResponseEntity<T> findemail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	public ResponseEntity<Page<T>> findPage(String name, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	public ResponseEntity<String> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/baseall", method = RequestMethod.GET)
	public ResponseEntity<List<BaseDto>> findBaseAll() {
		return ResponseEntity.ok(service().findBaseAll());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/perfils", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getRules() {
		List<String> lista = new LinkedList<>();
		for (Perfil perfil : Perfil.values()) {
			lista.add(perfil.getDescricao());
		}
		return ResponseEntity.ok(lista);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/funcoes", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getfuncaoes() {
		List<String> lista = new LinkedList<>();
		for (FuncaoEnum perfil : FuncaoEnum.values()) {
			lista.add(perfil.getDescricao());
		}
		return ResponseEntity.ok(lista);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service().delete(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/viewpdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> viewpdf() throws JRException, IOException {

		return ResponseEntity.ok(service().ViewPdf());
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/sendmail", method = RequestMethod.PUT)
	public ResponseEntity<Void> sendmail(@RequestBody EmailProperties properties) {

		service().sendemailreport(properties);
		return ResponseEntity.noContent().build();
	}
}
