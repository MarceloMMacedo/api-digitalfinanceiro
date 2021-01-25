package br.com.apidigitalfinanceiro.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalfinanceiro.converts.FormaPagamentoConverter;
import br.com.apidigitalfinanceiro.converts.NaturezaMovimentoConverter;
import br.com.apidigitalfinanceiro.converts.StatusConverter;
import br.com.apidigitalfinanceiro.converts.TipoMovimentoConverter;
import br.com.apidigitalfinanceiro.domain.intefaces.BaseEntity;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import lombok.Data;

@Data
@Entity
public class Fatura implements Serializable, BaseEntity {
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;

	private String name;
	
	private String numerodocumento;
	
	private String imagemfatura;
	
	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private ContasBanco bancopagador;
	
	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Funcionarios funcionario;
	
	@Convert(converter = FormaPagamentoConverter.class)
	private String formapagamento;

	@Convert(converter = StatusConverter.class)
	private String status;

	@Convert(converter = NaturezaMovimentoConverter.class)
	private String naturezamovimento;

	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	private GrupoFinanceiro historico;
	
	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	private OrdemServico ordemservico;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valor;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date instantCreation;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataMovimento;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataVencimento;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataQuitacao;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataleitura;

	@NumberFormat(style = Style.DEFAULT, pattern = "0")
	private int parcela;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double jurus;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double multa;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double desconto;

	@Convert(converter = TipoMovimentoConverter.class)
	private String tipomovimento;
	
	@Transient
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double total;

	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private MovimentoFinanceiro movimentoFinanceiro;


	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private OrdemServico os;
	
	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private Empresas empresa;
	
	@ManyToOne
	@JoinColumn(columnDefinition = " int")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private Contrato contrato;

	@Transient
	private List<AgregadoFinanceiro> agregadoFinanceiros = new ArrayList<AgregadoFinanceiro>();
	
	@Transient
	private String imagemfaturaView;

	public Fatura() {
	}

	public Fatura(MovimentoFinanceiro movementContrato, double value) {
		super();
		this.movimentoFinanceiro = movementContrato;
		this.name = movementContrato.getName();
		this.instantCreation = new Date();
		this.dataVencimento = new Date();
		this.dataQuitacao = new Date();
		this.valor = movementContrato.getValor();
		this.jurus = 0.0;
		this.desconto = 0.0;
		tipomovimento = movementContrato.getTipomovimento();
		this.status = StatusActiv.ABERTO.getDescricao();
		this.multa = 0.0;
		historico = movementContrato.getHistorico();

	}

	public List<AgregadoFinanceiro> getAgregadoFinanceiros() {
		agregadoFinanceiros = new LinkedList<AgregadoFinanceiro>();
		return agregadoFinanceiros;
	}

	public void setAgregadoFinanceiros(List<AgregadoFinanceiro> agregadoFinanceiros) {
		this.agregadoFinanceiros = agregadoFinanceiros;
	}

	public double getTotal() {
		total = 0;
		try {
			total = getValor() - getDesconto() + getJurus() + getMulta();
		} catch (Exception e) {
		}
		return total;
	}

}
