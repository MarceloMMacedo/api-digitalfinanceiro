package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResumoContasMovimento implements Serializable,ResumoInterface {
 
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String historico;
	
	private int exercicio;
	
	 
	private List<ResumoContasMovimento> listahst=new ArrayList<ResumoContasMovimento>();
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double saldoanterior;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double saldoExercioAtual;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double saldoexercicioPosterior;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double total;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double jan;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double fev;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double mar;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double abl;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double mai;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double jun;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double jul;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double ago;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double set;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double out;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double nov;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double dez;

	public ResumoContasMovimento(Integer id, String historico, int exercicio, double saldoanterior, double jan, double fev,
			double mar, double abl, double mai, double jun, double jul, double ago, double set, double out, double nov,
			double dez) {
		super();
		this.id = id;
		this.historico = historico;
		this.exercicio = exercicio;
		this.saldoanterior = saldoanterior;
		this.jan = jan;
		this.fev = fev;
		this.mar = mar;
		this.abl = abl;
		this.mai = mai;
		this.jun = jun;
		this.jul = jul;
		this.ago = ago;
		this.set = set;
		this.out = out;
		this.nov = nov;
		this.dez = dez;
	}

	public double getTotal() {
		total=0;
		try {
			total+=getSaldoanterior() + getSaldoExercioAtual()+ getSaldoexercicioPosterior();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	
 
}
