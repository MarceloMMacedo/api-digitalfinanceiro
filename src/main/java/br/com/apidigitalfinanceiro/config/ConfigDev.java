package br.com.apidigitalfinanceiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.apidigitalfinanceiro.IntTable;

@Configuration
@Profile(value = "dev")
public class ConfigDev {
 	@Autowired
	IntTable intTable; 

	@Bean
	public boolean initTabge() {
		 intTable.intTable();
		return true;
	}
}
