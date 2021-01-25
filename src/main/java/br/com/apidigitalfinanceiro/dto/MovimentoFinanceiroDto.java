package br.com.apidigitalfinanceiro.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.apidigitalfinanceiro.domain.MovimentoFinanceiro;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovimentoFinanceiroDto {

	private int id;
	private String name;
	private java.util.Date dataVencimento;
	private int parcela;
	private double valor;
	private String historico;
	private double valorAberto;
	private double valorQuit;
	
	private List<FaturaDto> faturas=new ArrayList<FaturaDto>();

	public MovimentoFinanceiroDto( MovimentoFinanceiro m) {
		 
		this.id =m.getId();
		this.name = m.getName();
		this.dataVencimento = m.getDataVencimento();
		this.parcela = m.getParcela();
		this.valor = m.getValor();
		valorAberto=m.getValorAberto();
		valorQuit=m.getValorQuit();
		this.historico =m.getHistorico().getName(); 
	}
	
	
	 
}
