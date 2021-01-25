package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode 
public class ItemMesDemostrativoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private int mes;

	private int exercicio;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valoresSaidaAberto;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valoresSaidaQuit;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valoresEntradaAberto;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valoresEntradaQuit;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double faturado;

	public double getFaturado() {
		faturado = 0;
		try {
			faturado = valoresEntradaQuit - valoresSaidaQuit;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return faturado;
	}

	public ItemMesDemostrativoDTO(  int mes2, int exercicio2, Object object, Object object2, Object object3,
			Object object4 ) {
		this.mes = mes2;
		this.exercicio = exercicio2;
		this.valoresSaidaAberto = (double) object;
		this.valoresSaidaQuit = (double) object2;
		this.valoresEntradaAberto = (double) object3;
		this.valoresEntradaQuit = (double) object4;
	}

 

}
