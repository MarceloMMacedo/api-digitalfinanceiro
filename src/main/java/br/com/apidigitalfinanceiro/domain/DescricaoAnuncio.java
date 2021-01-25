package br.com.apidigitalfinanceiro.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalfinanceiro.domain.intefaces.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity 
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class DescricaoAnuncio implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;

	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private Empresas empresa;

	private String titulo;

	private String descricao;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private AnuncioProduto anuncio;

	public DescricaoAnuncio(String titulo, String descricao) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
	}

}
