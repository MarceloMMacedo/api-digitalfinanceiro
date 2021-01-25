package br.com.apidigitalfinanceiro.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalfinanceiro.domain.Fatura;
import lombok.Data;

@Data 
public class FaturaContratoDto {

	private Integer id;
	private String name;
	private String numerodocumento;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataVencimento;

	@NumberFormat(style = Style.DEFAULT, pattern = "0")
	private int parcela;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double jurus;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double multa;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double desconto;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valor;

	private MultipartFile file;
	private Integer idbanco;
	private String formapagamento;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd/MM/yyyy")
	private Date dataquitacao;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FichaLeituraDto> fichaLeitura = new LinkedList<FichaLeituraDto>();

	private String status;
	public FaturaContratoDto() {

	}

	public FaturaContratoDto(Fatura f) {
		id = f.getId();
		name = f.getName();
		dataVencimento = f.getDataVencimento();
		parcela = f.getParcela();
		valor = f.getValor();
		status=f.getStatus();
	}

}
