package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;

import br.com.apidigitalfinanceiro.services.BaseInterfaceDto;
import lombok.Data;

@Data
public class BaseDto implements BaseInterfaceDto, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String cpfOnCnpj;

	private String avatar;

	private String avatarView;

	private String email;

	private double valor;

	public BaseDto() {
	}

	public BaseDto(Integer id, String name, String cpfOnCnpj, String avatar, String avatarView, String email) {
		super();
		this.id = id;
		this.name = name;
		this.cpfOnCnpj = cpfOnCnpj;
		this.avatar = avatar;
		this.avatarView = avatarView;

		this.email = email;
	}

}