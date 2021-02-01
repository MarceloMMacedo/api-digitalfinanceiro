package br.com.apidigitalfinanceiro.controllers;

import java.io.IOException;
import java.util.ArrayList;
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

import br.com.apidigitalfinanceiro.domain.Patrimonio;
import br.com.apidigitalfinanceiro.dto.BaseDto;
import br.com.apidigitalfinanceiro.enuns.TipoPatrimonioEnum;
import br.com.apidigitalfinanceiro.mail.storage.EmailProperties;
import br.com.apidigitalfinanceiro.services.PatrionioService;
import br.com.apidigitalfinanceiro.services.ServiceImpl;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value = "/patrimonios")
public class PatrimonioController extends ControllerImp<Patrimonio> {
	private static final long serialVersionUID = 1L;

	@Autowired
	PatrionioService service;

	@Override
	public ServiceImpl<Patrimonio> service() {
		return service;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}/picture", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) throws IOException {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.imagAvatar(file, id));
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/{id}/picturremocao", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProfilePictureremocao(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) throws IOException {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.imagRemocao(file, id));
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/typepatrimonio", method = RequestMethod.GET)
	public ResponseEntity<List<BaseDto>> typepatrimonio() {
		List<BaseDto> baseDto = new ArrayList<>();
		for (TipoPatrimonioEnum p : TipoPatrimonioEnum.values()) {
			baseDto.add(new BaseDto(p.getId(), p.getDescricao(), null, null, null, null));
		}
		return ResponseEntity.ok(baseDto);
	}

	

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMG' , 'ROLE_OPF' , 'ROLE_ADMEST'  )")
	@RequestMapping(value = "/viewpdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> viewpdf() throws JRException, IOException {

		return ResponseEntity.ok(service.ViewPdf());
	}
}
