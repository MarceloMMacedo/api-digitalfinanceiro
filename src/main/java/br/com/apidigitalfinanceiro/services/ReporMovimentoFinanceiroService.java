package br.com.apidigitalfinanceiro.services;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.Fatura;
import br.com.apidigitalfinanceiro.dto.DemosntrativoFinanceiroDto;
import br.com.apidigitalfinanceiro.dto.ItemDemosntrativoFinanceiroDto;
import br.com.apidigitalfinanceiro.dto.ItemMesDemostrativoDTO;
import br.com.apidigitalfinanceiro.dto.LivroCaixaDto;
import br.com.apidigitalfinanceiro.dto.ReportDemostrativoFinancerio;
import br.com.apidigitalfinanceiro.dto.ResumoMovimentoFinaneiro;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.enuns.TipoMovimentoEnum;
import br.com.apidigitalfinanceiro.repository.FaturaRepository;
import br.com.apidigitalfinanceiro.utils.FilesService;
import net.sf.jasperreports.engine.JRException;

@Service
public class ReporMovimentoFinanceiroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	MovimentoSaidaServices saidaServices;

	@Autowired
	MovimentoContratoServices contratoServices;

	@Autowired
	FaturaRepository faturaRepository;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	FilesService filesService;

	public ResumoMovimentoFinaneiro resumoexercicios() {
		ResumoMovimentoFinaneiro resumoMovimentoFinaneiros = new ResumoMovimentoFinaneiro();
		resumoMovimentoFinaneiros.setResumoPagar(saidaServices.resumocontaspagarsexercico());
		resumoMovimentoFinaneiros.setResumoReceberContrato(contratoServices.resumocontasrecebersexercico());
		resumoMovimentoFinaneiros.getEvents().addAll(saidaServices.createEvent());
		resumoMovimentoFinaneiros.getEvents().addAll(contratoServices.createEvent());
		return resumoMovimentoFinaneiros;
	}

	public DemosntrativoFinanceiroDto demonstrativoatual() {
		DemosntrativoFinanceiroDto demonstrativo = new DemosntrativoFinanceiroDto();
		// selecionar data
		int mes;
		int ano;
		SimpleDateFormat exercicio = new SimpleDateFormat("yyyy");
		ano = Integer.valueOf(exercicio.format(new Date()));
		SimpleDateFormat periodo = new SimpleDateFormat("MM");
		mes = Integer.valueOf(periodo.format(new Date()));

		// entradarealizados Resumo

		String tipomovimento_descricao = TipoMovimentoEnum.entradaContrato.getDescricao();
		int tipomovimento_id = TipoMovimentoEnum.entradaContrato.getId();

		List<Object[]> objects = new ArrayList<>();

		List<ItemDemosntrativoFinanceiroDto> entradas = new ArrayList<>();

		objects = faturaRepository.faturasdemosntrativofinanceiro(tipomovimento_id, ano, mes);
		for (Object[] obj : objects) {
			ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[2], obj[3],
					obj[4]);
			entradas.add(e);
		}

		// entradasarealizar a Realizar
		List<ItemDemosntrativoFinanceiroDto> movimentosaberto = new ArrayList<>();

		objects = faturaRepository.faturasItensdemosntrativofinanceiro(ano, mes);
		for (Object[] obj : objects) {
			ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[2], obj[3],
					obj[4], obj[5]);
			demonstrativo.getMovimentosAberto().add(e);
		}
		// Entrada OS Resumo

		tipomovimento_descricao = TipoMovimentoEnum.EntradaOrdemServico.getDescricao();
		tipomovimento_id = TipoMovimentoEnum.EntradaOrdemServico.getId();

		List<ItemDemosntrativoFinanceiroDto> entradarealizadosos = new ArrayList<>();
		objects = faturaRepository.faturasdemosntrativofinanceiro(tipomovimento_id, ano, mes);
		for (Object[] obj : objects) {
			ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[2], obj[3],
					obj[4]);
			entradarealizadosos.add(e);
		}
		for (ItemDemosntrativoFinanceiroDto itemDemosntrativoFinanceiroDto : entradarealizadosos) {
			entradas.add(itemDemosntrativoFinanceiroDto);
		}

		// Saida Resumo

		tipomovimento_descricao = TipoMovimentoEnum.Saida.getDescricao();
		tipomovimento_id = TipoMovimentoEnum.Saida.getId();

		List<ItemDemosntrativoFinanceiroDto> saidas = new ArrayList<>();
		objects = faturaRepository.faturasdemosntrativofinanceiro(tipomovimento_id, ano, mes);
		for (Object[] obj : objects) {
			ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[2], obj[3],
					obj[4]);
			saidas.add(e);
		}
		// entradasarealizar a Realizar

		double realizados = 0;
		double futuros = 0;
		for (ItemDemosntrativoFinanceiroDto item : entradas) {
			realizados += item.getValorrealizado();
			futuros += item.getValorrealizar();
		}
		demonstrativo.setEstradasRealizadas(realizados);
		demonstrativo.setEstradasFuturas(futuros);

		realizados = 0;
		futuros = 0;
		for (ItemDemosntrativoFinanceiroDto item : saidas) {
			realizados += item.getValorrealizado();
			futuros += item.getValorrealizar();
		}
		demonstrativo.setSaidasRealizadas(realizados);
		demonstrativo.setSaidasFuturas(futuros);

		demonstrativo.setEntradarealizados(entradas);

		demonstrativo.setSaidarealizados(saidas);

		demonstrativo.setExercicio(ano);
		return demonstrativo;

	}

	public DemosntrativoFinanceiroDto demonstrativoatual(int ano, int mes) {
		DemosntrativoFinanceiroDto demonstrativo = new DemosntrativoFinanceiroDto();
		// selecionar data

		// entradarealizados Resumo
		double realizados = 0;
		double futuros = 0;
		String tipomovimento_descricao = TipoMovimentoEnum.entradaContrato.getDescricao();
		int tipomovimento_id = TipoMovimentoEnum.entradaContrato.getId();

		List<Object[]> objects = new ArrayList<>();

		List<ItemDemosntrativoFinanceiroDto> entradas = new ArrayList<>();

		objects = faturaRepository.faturasdemosntrativofinanceiro(tipomovimento_id, ano, mes);
		for (Object[] obj : objects) {
			if ((Integer) obj[2] == StatusActiv.QUIT.getId()) {
				ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[3], obj[4]);
				realizados += e.getValorrealizado();
				entradas.add(e);
			} else {
				futuros += (double) obj[3];
			}
		}

		// entradasarealizar a Realizar
		List<ItemDemosntrativoFinanceiroDto> movimentosaberto = new ArrayList<>();

		objects = faturaRepository.faturasItensdemosntrativofinanceiro(ano, mes);
		for (Object[] obj : objects) {
			ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[2], obj[3],
					obj[4], obj[5]);
			demonstrativo.getMovimentosAberto().add(e);
		}
		// Entrada OS Resumo

		tipomovimento_descricao = TipoMovimentoEnum.EntradaOrdemServico.getDescricao();
		tipomovimento_id = TipoMovimentoEnum.EntradaOrdemServico.getId();

		List<ItemDemosntrativoFinanceiroDto> entradarealizadosos = new ArrayList<>();
		objects = faturaRepository.faturasdemosntrativofinanceiro(tipomovimento_id, ano, mes);
		for (Object[] obj : objects) {
			if ((Integer) obj[2] == StatusActiv.QUIT.getId()) {
				ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[2], obj[3],
						obj[4]);
				entradarealizadosos.add(e);
				realizados += e.getValorrealizado();
			} else {
				futuros += (double) obj[3];
			}
		}
		for (ItemDemosntrativoFinanceiroDto itemDemosntrativoFinanceiroDto : entradarealizadosos) {
			entradas.add(itemDemosntrativoFinanceiroDto);
		}
		demonstrativo.setEstradasRealizadas(realizados);
		demonstrativo.setEstradasFuturas(futuros);
		// Saida Resumo

		tipomovimento_descricao = TipoMovimentoEnum.Saida.getDescricao();
		tipomovimento_id = TipoMovimentoEnum.Saida.getId();
		realizados = 0;
		futuros = 0;
		List<ItemDemosntrativoFinanceiroDto> saidas = new ArrayList<>();
		objects = faturaRepository.faturasdemosntrativofinanceiro(tipomovimento_id, ano, mes);
		for (Object[] obj : objects) {
			if ((Integer) obj[2] == StatusActiv.QUIT.getId()) {
				ItemDemosntrativoFinanceiroDto e = new ItemDemosntrativoFinanceiroDto(obj[0], obj[1], obj[3], obj[4]);
				realizados += e.getValorrealizado();
				saidas.add(e);
			} else {
				futuros += (double) obj[3];
			}
		}

		demonstrativo.setSaidasRealizadas(realizados);

		demonstrativo.setSaidasFuturas(futuros);

		// entradasarealizar a Realizar

		/*
		 * for (ItemDemosntrativoFinanceiroDto item : entradas) { realizados +=
		 * item.getValorrealizado(); futuros += item.getValorrealizar(); }
		 */
		// demonstrativo.setEstradasRealizadas(realizados);
		// demonstrativo.setEstradasFuturas(futuros);

		realizados = 0;
		futuros = 0;

		/*
		 * for (ItemDemosntrativoFinanceiroDto item : saidas) { realizados +=
		 * item.getValorrealizado(); futuros += item.getValorrealizar(); }
		 * demonstrativo.setSaidasRealizadas(realizados);
		 * 
		 * demonstrativo.setSaidasFuturas(futuros);
		 * 
		 */
		demonstrativo.setEntradarealizados(entradas);
		demonstrativo.setSaidarealizados(saidas);

		demonstrativo.setExercicio(ano);

		demonstrativo.setMes(mes);
		return demonstrativo;

	}

	 public byte[] ViewPdf() throws JRException, IOException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String s = "";
		Resource res = resourceLoader.getResource("classpath:templates/report/demonstrativofinanceiro/");
		try {
			s = res.getFile().getAbsolutePath();
			System.out.println(res.getURI());
		} catch (IOException e) {

			e.printStackTrace();
		}
		parameters.put("pathentrada", s + "/entradas.jasper");
		parameters.put("pathsaida", s + "/saidas.jasper");
		parameters.put("PathMovimento", s + "/movimentoaberto.jasper");

		List<?> source = new ArrayList<>();

		String templates = res.getFile().getAbsolutePath() + "/demonstrativo.jasper";
		List<DemosntrativoFinanceiroDto> list = new ArrayList<DemosntrativoFinanceiroDto>();
		list.add(demonstrativoatual());
		source = list;
		return filesService.ViewPdf(parameters, source, templates);
	}
 
	public byte[] ViewPdf(int ano, int mes) throws JRException, IOException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		/*String s = "";
		Resource res = resourceLoader.getResource("classpath:templates/report/demonstrativofinanceiro/");
		try {
			s = res.getFile().getAbsolutePath();
			System.out.println(res.getFile().getAbsolutePath());
		} catch (IOException e) {

			e.printStackTrace();
		}

//
		System.out.println(s + "/entradas.jasper");
		parameters.put("entradas", demonstrativoatual.getEntradarealizados());
		parameters.put("pathentrada", s + "/entradas.jasper");
		parameters.put("pathsaida", s + "/saidas.jasper");
		parameters.put("PathMovimento", s + "/movimentoaberto.jasper");
*/
		
		parameters.put("pathentrada", filesService.loadPathJasperFile( "resumodemonstrativo")); 

		parameters.put("PathMovimento",filesService.loadPathJasperFile( "movimentoaberto"));
		
		DemosntrativoFinanceiroDto demonstrativoatual = demonstrativoatual(ano, mes);
		List<DemosntrativoFinanceiroDto> source = new ArrayList<DemosntrativoFinanceiroDto>();

		String templates = "demonstrativo";
		List<DemosntrativoFinanceiroDto> list = new ArrayList<DemosntrativoFinanceiroDto>();
		list.add(demonstrativoatual);
		source = list;
		return filesService.ViewPdf(parameters, source, templates);
	}
 
	public byte[] viewpddemonstrativosintetico() throws JRException, IOException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		 
		List<?> source = new ArrayList<>();

		String templates = "resumodemonstrativosintetico";
		List<ReportDemostrativoFinancerio> list = new LinkedList<ReportDemostrativoFinancerio>();
		list = (reportdemostrativofinancerio());
		source = list;
		return filesService.ViewPdf(parameters, source, templates);
	}

	public byte[] viewpddemonstrativosintetico(int exercicio) throws JRException, IOException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String s = "";
		 

		List<?> source = new ArrayList<>();

		String templates = "resumodemonstrativosintetico";
		List<ReportDemostrativoFinancerio> list = new LinkedList<ReportDemostrativoFinancerio>();
		list = (reportdemostrativofinancerio(exercicio));
		source = list;
		return filesService.ViewPdf(parameters, source, templates);
	}

	public List<ReportDemostrativoFinancerio> reportdemostrativofinancerio() {
		List<ReportDemostrativoFinancerio> reportdemostrativofinancerio = new ArrayList<ReportDemostrativoFinancerio>();
		List<Object[]> faturasExercicioSomentedemosntrativofinanceiro = faturaRepository
				.faturasExercicioSomentedemosntrativofinanceiro();

		for (Object[] objects : faturasExercicioSomentedemosntrativofinanceiro) {
			ReportDemostrativoFinancerio demostrativoFinancerio = new ReportDemostrativoFinancerio((int) objects[0]);
			demostrativoFinancerio.setJan(setMes(1, (int) objects[0]));
			demostrativoFinancerio.setFev(setMes(2, (int) objects[0]));
			demostrativoFinancerio.setMar(setMes(3, (int) objects[0]));
			demostrativoFinancerio.setAbr(setMes(4, (int) objects[0]));
			demostrativoFinancerio.setMai(setMes(5, (int) objects[0]));
			demostrativoFinancerio.setJun(setMes(6, (int) objects[0]));
			demostrativoFinancerio.setJul(setMes(7, (int) objects[0]));
			demostrativoFinancerio.setAgo(setMes(8, (int) objects[0]));
			demostrativoFinancerio.setSet(setMes(9, (int) objects[0]));
			demostrativoFinancerio.setOut(setMes(10, (int) objects[0]));
			demostrativoFinancerio.setNov(setMes(11, (int) objects[0]));
			demostrativoFinancerio.setDez(setMes(12, (int) objects[0]));
			reportdemostrativofinancerio.add(demostrativoFinancerio);
		}

		return reportdemostrativofinancerio;
	}

	public List<ReportDemostrativoFinancerio> reportdemostrativofinancerio(int exercicio) {
		List<ReportDemostrativoFinancerio> reportdemostrativofinancerio = new ArrayList<ReportDemostrativoFinancerio>();
		/*
		 * List<Object[]> faturasExercicioSomentedemosntrativofinanceiro =
		 * faturaRepository .faturasExercicioSomentedemosntrativofinanceiro();
		 * 
		 * for (Object[] objects : faturasExercicioSomentedemosntrativofinanceiro)
		 */
		{
			ReportDemostrativoFinancerio demostrativoFinancerio = new ReportDemostrativoFinancerio(exercicio);
			demostrativoFinancerio.setJan(setMes(1, exercicio));
			demostrativoFinancerio.setFev(setMes(2, exercicio));
			demostrativoFinancerio.setMar(setMes(3, exercicio));
			demostrativoFinancerio.setAbr(setMes(4, exercicio));
			demostrativoFinancerio.setMai(setMes(5, exercicio));
			demostrativoFinancerio.setJun(setMes(6, exercicio));
			demostrativoFinancerio.setJul(setMes(7, exercicio));
			demostrativoFinancerio.setAgo(setMes(8, exercicio));
			demostrativoFinancerio.setSet(setMes(9, exercicio));
			demostrativoFinancerio.setOut(setMes(10, exercicio));
			demostrativoFinancerio.setNov(setMes(11, exercicio));
			demostrativoFinancerio.setDez(setMes(12, exercicio));
			reportdemostrativofinancerio.add(demostrativoFinancerio);
		}

		return reportdemostrativofinancerio;
	}

	private ItemMesDemostrativoDTO setMes(int mes, int exercicio) {
		ItemMesDemostrativoDTO setMes = new ItemMesDemostrativoDTO();
		List<Object[]> faLists = faturaRepository.faturasSomenteItensdemosntrativofinanceiro(exercicio, mes);
		setMes.setExercicio(exercicio);
		setMes.setMes(mes);
		Object[] faList = faLists.get(0);
		setMes.setValoresSaidaAberto((Double) faList[0]);
		setMes.setValoresSaidaQuit((Double) faList[1]);
		setMes.setValoresEntradaAberto((Double) faList[2]);
		setMes.setValoresEntradaQuit((Double) faList[3]);

		return setMes;
	}

	public LivroCaixaDto caixaDto(Date datainicio, Date datafim) {
		List<ItemDemosntrativoFinanceiroDto> movimentos = new ArrayList<>();
		
		double entradas = 0;
		double saidas = 0;
		List<Fatura> faturas = faturaRepository.livroCaixa(datainicio, datafim, StatusActiv.QUIT.getDescricao());
		ItemDemosntrativoFinanceiroDto dto;
		try {
			for (Fatura fatura : faturas) {
				dto = new ItemDemosntrativoFinanceiroDto(fatura);
				movimentos.add(dto);
				if (fatura.getTipomovimento().equals(TipoMovimentoEnum.Saida)) {
					saidas += fatura.getTotal();
				} else
						entradas += fatura.getTotal();
				;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LivroCaixaDto caixaDto = new LivroCaixaDto(movimentos, datainicio, datafim, entradas, saidas);
		return caixaDto;
	}

	 public byte[] ViewPdflivrocaixa(Date datainicio, Date datafim) throws JRException, IOException {
			Map<String, Object> parameters = new HashMap<String, Object>();
			String s = "";
			parameters.put("PathMovimento",filesService.loadPathJasperFile( "movimentolivrocaixa"));
			

			String templates =   "livrocaixa";
			List<LivroCaixaDto> list = new ArrayList<LivroCaixaDto>();
			list.add(caixaDto(datainicio, datafim));
			 
			return filesService.ViewPdf(parameters, list, templates);
		}
}
