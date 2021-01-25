package br.com.apidigitalfinanceiro.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.apidigitalfinanceiro.domain.intefaces.BaseEntity;
import br.com.apidigitalfinanceiro.dto.BaseDto;
import net.sf.jasperreports.engine.JRException;

public interface ControllerInterfaces<T extends BaseEntity> {

	public Class<T> getClasse();

	public ResponseEntity<List<T>> findAll();

	public ResponseEntity<T> find(@PathVariable Integer id);

	public ResponseEntity<Integer> insert(@Validated @RequestBody T objDto);

	public ResponseEntity<Void> update(@Validated @RequestBody T objDto, @PathVariable Integer id);

	public ResponseEntity<T> findemail(@RequestParam(value = "value") String email);

	public ResponseEntity<Page<T>> findPage(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction);

	public ResponseEntity<String> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) throws IOException;

	ResponseEntity<List<BaseDto>> findBaseAll();

	public ResponseEntity<List<String>> getRules();

	public ResponseEntity<List<String>> getfuncaoes();

	public ResponseEntity<Void> delete(@PathVariable Integer id);
	
	public ResponseEntity< byte[] > viewpdf( ) throws JRException, IOException ;
}
