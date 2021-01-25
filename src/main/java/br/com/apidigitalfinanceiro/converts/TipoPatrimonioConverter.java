package br.com.apidigitalfinanceiro.converts;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalfinanceiro.enuns.TipoPatrimonioEnum;

@Converter
public class TipoPatrimonioConverter implements
AttributeConverter<String, Integer>{

	@Override
	public Integer convertToDatabaseColumn(String attribute) { 
		return TipoPatrimonioEnum.findById(attribute);
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) { 
		return  TipoPatrimonioEnum.getById(dbData);
	}

	 
}
