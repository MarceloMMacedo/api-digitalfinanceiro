package br.com.apidigitalfinanceiro.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.apidigitalfinanceiro.converts.NaturezaMovimentoConverter;
import br.com.apidigitalfinanceiro.domain.intefaces.BaseEntity;
import lombok.Data;

@Entity
@Data
public class MovimentoBanco implements Serializable, BaseEntity {
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valor;
	

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataMovimento;
	 
	 
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Fatura fatura;

	@Convert(converter = NaturezaMovimentoConverter.class)
	private String naturezaMovimento;
	

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private ContasBanco contasBanco;


	@ManyToOne
	@JoinColumn(columnDefinition = " int ")
	@JsonIgnore
	private Empresas empresa;
	
	public MovimentoBanco() {
		// TODO Auto-generated constructor stub
	}

	 

}
