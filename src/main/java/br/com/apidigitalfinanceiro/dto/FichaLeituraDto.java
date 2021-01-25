package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.apidigitalfinanceiro.domain.FichaLeitura;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FichaLeituraDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Integer idfatura;
	private Integer idequipamento;
	private String modelo;
	private String local;
	private Integer franquia;
	private Integer creditos;
	private double valorfranquia;
	private String serial;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataleitura;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataprogramadaleitura;

	private String status;
	private int medidorAtual;
	private int medidorAnterior;
 
	private int medidorFaturamento = 0;
	private int faturamentoExcedente;
	private double valorExcedente;

	public FichaLeituraDto(FichaLeitura fichaLeitura) {
		super();
		this.id = fichaLeitura.getId();
		this.descricao = fichaLeitura.getDescricao();
		this.idfatura = fichaLeitura.getFatura().getId();
		this.idequipamento = fichaLeitura.getEquipamentoContrato().getId();
		this.serial = fichaLeitura.getEquipamentoContrato().getEquipamento().getSerial();
		this.modelo = fichaLeitura.getEquipamentoContrato().getEquipamento().getModelo().getName();
		this.local = fichaLeitura.getEquipamentoContrato().getSetor();
		this.franquia = fichaLeitura.getEquipamentoContrato().getFranquia();
		this.creditos = 0;
		this.valorfranquia = fichaLeitura.getEquipamentoContrato().getValorfranquia();
		this.dataleitura = new Date();
		this.dataprogramadaleitura = fichaLeitura.getFatura().getDataleitura();
		this.status = fichaLeitura.getStatus();
		this.medidorAtual = 0;
		this.medidorAnterior = fichaLeitura.getEquipamentoContrato().getEquipamento().getMedidorContrato();
		this.medidorFaturamento = 0;
		this.faturamentoExcedente = 0;
		this.valorExcedente = 0;

	}

	public int getMedidorFaturamento() {
		int medidorFaturamento = 0;
		try {
			medidorFaturamento = medidorAnterior - medidorAtual;
		} catch (Exception e) { 
		}
		return medidorFaturamento;
	}

	public int getFaturamentoExcedente() {
		int faturamentoExcedente = 0;
		try {
			faturamentoExcedente = getMedidorFaturamento() - franquia;
			if (faturamentoExcedente < 0)
				faturamentoExcedente = 0;
		} catch (Exception e) { 
		}
		return faturamentoExcedente;
	}

	public double getValorExcedente() {
		double valorExcedente = 0;
		try {
			valorExcedente = getFaturamentoExcedente() * valorExcedente;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorExcedente;
	}

}
