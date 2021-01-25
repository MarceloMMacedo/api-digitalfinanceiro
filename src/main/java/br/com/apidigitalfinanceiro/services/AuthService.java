package br.com.apidigitalfinanceiro.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.Funcionarios;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.repository.FuncionariosRepository;
import br.com.apidigitalfinanceiro.service.security.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private FuncionariosRepository employeeRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	/*
	 * @Autowired private EmailService emailService;
	 */

	private Random rand = new Random();

	public void sendNewPassword(String email) {

		Funcionarios employee = employeeRepository.findByEmailAndStatus(email,  StatusActiv.ATIVO.getDescricao() );
		if (employee == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String newPass = newPassword();
		employee.setSenha(pe.encode(newPass));

		employeeRepository.save(employee);
		// emailService.sendNewPasswordEmail(employee, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
