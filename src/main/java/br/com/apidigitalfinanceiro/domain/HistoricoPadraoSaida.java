package br.com.apidigitalfinanceiro.domain;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class HistoricoPadraoSaida extends GrupoFinanceiro implements Serializable {

	private static final long serialVersionUID = 1L;

	public HistoricoPadraoSaida() { 
	}
}
