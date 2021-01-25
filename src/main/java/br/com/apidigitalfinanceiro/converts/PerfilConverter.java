package br.com.apidigitalfinanceiro.converts;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalfinanceiro.enuns.Perfil;

@Converter
public class PerfilConverter implements
AttributeConverter<String, String>{

	@Override
	public String convertToDatabaseColumn(String attribute) {
		 
		return Perfil.getIdEnum(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) { 
		return Perfil.getDescricaoEnum(dbData);
	}

	 

	 
}
