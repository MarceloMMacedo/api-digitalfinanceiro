package br.com.apidigitalfinanceiro.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalfinanceiro.domain.intefaces.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@NoArgsConstructor
public class OrdemServico implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	
	

	@ManyToOne
	@JoinColumn(columnDefinition = " int ")
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Empresas empresa;
	 
	@Transient
	private double total;

}
