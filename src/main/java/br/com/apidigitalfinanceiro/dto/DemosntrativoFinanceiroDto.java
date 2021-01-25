package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DemosntrativoFinanceiroDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int exercicio;
	private int mes;
	private List<ItemDemosntrativoFinanceiroDto> entradarealizados = new ArrayList<>();
	private List<ItemDemosntrativoFinanceiroDto> entradasFuturo = new ArrayList<>();
	private List<ItemDemosntrativoFinanceiroDto> saidarealizados = new ArrayList<>();
	private List<ItemDemosntrativoFinanceiroDto> saidasFuturo = new ArrayList<>();
	private List<ItemDemosntrativoFinanceiroDto> movimentosAberto = new ArrayList<>();

	private double estradasRealizadas;
	private double estradasFuturas;
	private double saidasRealizadas;
	private double saidasFuturas;

	private double faturamentosRealizadas;
	private double faturamentosFuturas;

	private double saldo;

	public double getFaturamentosRealizadas() {
		faturamentosRealizadas = 0;
		faturamentosRealizadas = getEstradasRealizadas() - getSaidasRealizadas();
		return faturamentosRealizadas;
	}

	public double getFaturamentosFuturas() {
		faturamentosFuturas = 0;
		faturamentosFuturas = getEstradasFuturas() - getSaidasFuturas();
		return faturamentosFuturas;
	}

	public double getSaldo() {
		saldo = 0;
		saldo = getFaturamentosRealizadas() + getFaturamentosFuturas();
		return saldo;
	}
}
