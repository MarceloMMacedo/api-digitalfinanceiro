package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroCaixaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ItemDemosntrativoFinanceiroDto> movimentos = new ArrayList<>();
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date datainicio;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date datafim;
	

	private double totaisEntrada;
	private double totaisSaida;
	private double total;

	public LivroCaixaDto(List<ItemDemosntrativoFinanceiroDto> movimentos, Date datainicio, Date datafim,
			double totaisEntrada, double totaisSaida ) {
		super();
		this.movimentos = movimentos;
		this.datainicio = datainicio;
		this.datafim = datafim;
		this.totaisEntrada = totaisEntrada;
		this.totaisSaida = totaisSaida;
		this.total = totaisEntrada- totaisSaida;
	}

}
