package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.apidigitalfinanceiro.domain.Fatura;
import br.com.apidigitalfinanceiro.enuns.TipoMovimentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class ItemDemosntrativoFinanceiroDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String movimentofinanceiro;

	private String descricaofatura;

	private double valorrealizar;
	private double valorrealizado;
	private String moeda;
	private String banco;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date data;

	public ItemDemosntrativoFinanceiroDto(int id, String movimentofinanceiro, String descricaofatura,
			double valorrealizar, double valorrealizado) {
		super();
		this.id = id;
		this.movimentofinanceiro = movimentofinanceiro;
		this.descricaofatura = descricaofatura;
		this.valorrealizar = valorrealizar;
		this.valorrealizado = valorrealizado;
	}

	public ItemDemosntrativoFinanceiroDto(Object object, Object object2, Object object3, Object object4,
			Object object5) {
		this.id = Integer.valueOf(String.valueOf(object));
		this.movimentofinanceiro = (String) object2;
		this.descricaofatura = (String) object3;
		this.valorrealizar = (double) object4;
		this.valorrealizado = (double) object5;
	}

	public ItemDemosntrativoFinanceiroDto(Object object, Object object2, Object object4, Object object5) {
		this.id = Integer.valueOf(String.valueOf(object));
		this.movimentofinanceiro = (String) object2;
		this.valorrealizar = (double) object4;
		this.valorrealizado = (double) object5;
	}

	public ItemDemosntrativoFinanceiroDto(Object object, Object object2, Object object3, Object object4, Object object5,
			Object object6) {
		this.id = Integer.valueOf(String.valueOf(object));
		this.movimentofinanceiro = (String) object2;
		this.descricaofatura = (String) object3;
		this.valorrealizar = (double) object4;
		this.valorrealizado = (double) object5;
		data = (Date) object6;
	}

	public ItemDemosntrativoFinanceiroDto(Fatura f) {
		super();
		this.id = f.getId();
		this.movimentofinanceiro = f.getHistorico().getName();
		this.descricaofatura = f.getName();
		if (f.getTipomovimento().equals(TipoMovimentoEnum.Saida)) {
			this.valorrealizar = -1 * f.getTotal();
		} 
		this.moeda = f.getFormapagamento();
		this.banco = f.getBancopagador().getBanco();
		this.data = f.getDataQuitacao();
	}

}
