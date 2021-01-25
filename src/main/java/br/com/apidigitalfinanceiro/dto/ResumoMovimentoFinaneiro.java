package br.com.apidigitalfinanceiro.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class ResumoMovimentoFinaneiro implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ResumoContasMovimento> resumoPagar=new LinkedList<ResumoContasMovimento>();

	private List<ResumoContasMovimento> resumoReceberContrato=new LinkedList<ResumoContasMovimento>();

	private List<ResumoContasMovimento> resumoReceberServico=new LinkedList<ResumoContasMovimento>();

	private List<Events> events=new LinkedList<Events>();
}