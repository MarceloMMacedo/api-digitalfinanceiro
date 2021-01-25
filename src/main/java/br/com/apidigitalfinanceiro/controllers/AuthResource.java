package br.com.apidigitalfinanceiro.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalfinanceiro.config.security.JWTUtils;
import br.com.apidigitalfinanceiro.config.security.UserSS;
import br.com.apidigitalfinanceiro.config.services.UserService;
import br.com.apidigitalfinanceiro.dto.credenciais.EmailDTO;
import br.com.apidigitalfinanceiro.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtils jwtUtil;

	@Autowired
	private AuthService service;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		
		response.addHeader("ID_Company",String.valueOf( user.getEmpresa() ));	        
		response.addHeader("access-control-expose-headers", "ID_Company");
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot( @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
