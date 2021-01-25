package br.com.apidigitalfinanceiro.services;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.config.security.UserSS;
import br.com.apidigitalfinanceiro.config.services.UserService;
import br.com.apidigitalfinanceiro.domain.AgregadoFinanceiro;
import br.com.apidigitalfinanceiro.domain.AgregadoFinanceiroDto;
import br.com.apidigitalfinanceiro.domain.CentroCusto;
import br.com.apidigitalfinanceiro.domain.ContasBanco;
import br.com.apidigitalfinanceiro.domain.Contrato;
import br.com.apidigitalfinanceiro.domain.Fatura;
import br.com.apidigitalfinanceiro.domain.FichaLeitura;
import br.com.apidigitalfinanceiro.domain.MovimentoContrato;
import br.com.apidigitalfinanceiro.domain.Patrimonio;
import br.com.apidigitalfinanceiro.dto.Events;
import br.com.apidigitalfinanceiro.dto.FaturaContratoDto;
import br.com.apidigitalfinanceiro.dto.FichaLeituraDto;
import br.com.apidigitalfinanceiro.dto.ResumoContasMovimento;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.enuns.TipoMovimentoEnum;
import br.com.apidigitalfinanceiro.repository.CentroCustoRepository;
import br.com.apidigitalfinanceiro.repository.ContasBancoRepository;
import br.com.apidigitalfinanceiro.repository.FaturaRepository;
import br.com.apidigitalfinanceiro.repository.FichaLeituraRepository;
import br.com.apidigitalfinanceiro.repository.MovimentoContratoRepository;
import br.com.apidigitalfinanceiro.repository.PatrimonioRepository;
import br.com.apidigitalfinanceiro.service.security.exceptions.AuthorizationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MovimentoContratoServices extends ServiceImpl<MovimentoContrato> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	FaturaRepository faturaRepository;

	@Autowired
	MovimentoContratoRepository repo;

	@Autowired
	PatrimonioRepository patrimonioRepository;

	@Autowired
	ContasBancoRepository bancoRepository;
	@Autowired
	ContratoService contratoService;
	@Autowired
	FichaLeituraRepository fichaLeituraRepository;

	@Autowired
	CentroCustoRepository centroCustoRepository;

	@Override
	public JpaRepository<MovimentoContrato, Integer> repo() {
		return repo;
	}

	private Integer exercicio;

	@PostConstruct
	public void inicio() {
		SimpleDateFormat ano = new SimpleDateFormat("yyyy");
		exercicio = Integer.valueOf(ano.format(new Date()));
	}

	@Override
	public List<MovimentoContrato> findAll() {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		List<MovimentoContrato> find = repo.findAll();

		return find;
	}

	private List<ResumoContasMovimento> contasMovimento = new ArrayList<ResumoContasMovimento>();

	@SuppressWarnings("unused")
	private double totalmes(int exercico, int mes, String status, int id) {
		double valoresAnterio = 0;
		try {
			valoresAnterio = (double) faturaRepository.totalmovimentoFinanceiromes(exercico, mes, status, id,
					TipoMovimentoEnum.entradaContrato.getDescricao());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valoresAnterio;
	}

	@SuppressWarnings("unused")
	private double totalmovimentoFinanceiromes(int exercico, int mes, String status, int id) {
		double valoresAnterio = 0;
		try {
			valoresAnterio = (double) faturaRepository.totalmovimentoFinanceiromes(exercico, mes, status, id,
					TipoMovimentoEnum.entradaContrato.getDescricao());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valoresAnterio;
	}

	@SuppressWarnings("unused")
	private double totalmesByExercicio(int exercico, int mes, String status) {
		double valoresAnterio = 0;
		try {
			valoresAnterio = (double) faturaRepository.totalmesByExercicio(exercico, mes, status,
					TipoMovimentoEnum.entradaContrato.getDescricao());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valoresAnterio;
	}

	@SuppressWarnings("unused")
	private ResumoContasMovimento addlistahst(Object[] obj) {
		List<ResumoContasMovimento> listahst = new ArrayList<ResumoContasMovimento>();
		ResumoContasMovimento resumoContasMovimento = new ResumoContasMovimento();
		resumoContasMovimento.setExercicio(this.exercicio);
		resumoContasMovimento.setId((Integer) obj[0]);
		resumoContasMovimento.setHistorico((String) obj[1]);
		double valoresAnterio = 0;
		// ExercicoAnterior
		try {
			valoresAnterio = (double) faturaRepository.totalmovimentoFinanceiroAnterior(this.exercicio,
					StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId());
		} catch (Exception e) {
		}
		resumoContasMovimento.setSaldoanterior(valoresAnterio);

		// ExercicoAtual
		valoresAnterio = 0;
		try {
			valoresAnterio = (double) faturaRepository.totalemovimentoFinanceiroxercicioAtual(this.exercicio,
					StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId());
		} catch (Exception e) {
		}
		resumoContasMovimento.setSaldoExercioAtual(valoresAnterio);

		// ExercicoPostrior
		valoresAnterio = 0;
		try {
			valoresAnterio = (double) faturaRepository.totalmovimentoFinanceiroexercicioPosterior(this.exercicio,
					StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId());
		} catch (Exception e) {
		}
		resumoContasMovimento.setSaldoexercicioPosterior(valoresAnterio);

		// jan

		resumoContasMovimento.setJan(totalmovimentoFinanceiromes(this.exercicio, 1, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// fev

		resumoContasMovimento.setFev(totalmovimentoFinanceiromes(this.exercicio, 2, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// marv
		resumoContasMovimento.setMar(totalmovimentoFinanceiromes(this.exercicio, 3, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// abl
		resumoContasMovimento.setAbl(totalmovimentoFinanceiromes(this.exercicio, 4, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// Maio
		resumoContasMovimento.setMai(totalmovimentoFinanceiromes(this.exercicio, 5, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// jun
		resumoContasMovimento.setJun(totalmovimentoFinanceiromes(this.exercicio, 6, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// jul
		resumoContasMovimento.setJul(totalmovimentoFinanceiromes(this.exercicio, 7, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// ago 8
		resumoContasMovimento.setAgo(totalmovimentoFinanceiromes(this.exercicio, 8, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// set
		resumoContasMovimento.setSet(totalmovimentoFinanceiromes(this.exercicio, 9, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// out
		resumoContasMovimento.setOut(totalmovimentoFinanceiromes(this.exercicio, 10, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// nov
		resumoContasMovimento.setNov(totalmovimentoFinanceiromes(this.exercicio, 11, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));

		// dez
		resumoContasMovimento.setDez(totalmovimentoFinanceiromes(this.exercicio, 12, StatusActiv.ABERTO.getDescricao(),
				resumoContasMovimento.getId()));
		listahst.add(resumoContasMovimento);
		return resumoContasMovimento;
	}

	public List<ResumoContasMovimento> resumoContasRecebers(int exercico) {
		if (exercico > 0)
			this.exercicio = exercico;
		contasMovimento = new ArrayList<ResumoContasMovimento>();
		double valoresAnterio = 0;
		List<Object[]> movimentosf = repo.movimentosByFaturas(StatusActiv.ABERTO.getDescricao());

		for (Object[] resumoInterface : movimentosf) {
			valoresAnterio = 0;
			ResumoContasMovimento resumoContasMovimento = new ResumoContasMovimento();
			resumoContasMovimento.setId((Integer) resumoInterface[0]);
			resumoContasMovimento.setHistorico((String) resumoInterface[1]);

			// lista movimentos
			/*
			 * List<ResumoContasMovimento> listahst = new
			 * ArrayList<ResumoContasMovimento>(); List<Object[]> listhst =
			 * repo.movimentosByFaturasAndHistorico(StatusActiv.ABERTO.getDescricao(),
			 * (Integer) resumoInterface[0]); for (Object[] obj : listhst) {
			 * listahst.add(addlistahst(obj)); }
			 */
			// resumoContasMovimento.setListahst(listahst);
			resumoContasMovimento.setExercicio(this.exercicio);
			// ExercicoAnterior
			try {
				valoresAnterio = (double) faturaRepository.totalmovimentoFinanceiroAnterior(this.exercicio,
						StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId());
			} catch (Exception e) {
				// TODO: handle exception
			}
			resumoContasMovimento.setSaldoanterior(valoresAnterio);

			// ExercicoAtual
			valoresAnterio = 0;
			try {
				valoresAnterio = (double) faturaRepository.totalemovimentoFinanceiroxercicioAtual(this.exercicio,
						StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId());
			} catch (Exception e) {
				// TODO: handle exception
			}
			resumoContasMovimento.setSaldoExercioAtual(valoresAnterio);

			// ExercicoPostrior
			valoresAnterio = 0;
			try {
				valoresAnterio = (double) faturaRepository.totalmovimentoFinanceiroAnterior(this.exercicio,
						StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId());
			} catch (Exception e) {
				// TODO: handle exception
			}
			resumoContasMovimento.setSaldoexercicioPosterior(valoresAnterio);

			// jan

			resumoContasMovimento.setJan(
					totalmes(this.exercicio, 1, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// fev

			resumoContasMovimento.setFev(
					totalmes(this.exercicio, 2, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// marv
			resumoContasMovimento.setMar(
					totalmes(this.exercicio, 3, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// abl
			resumoContasMovimento.setAbl(
					totalmes(this.exercicio, 4, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// Maio
			resumoContasMovimento.setMai(
					totalmes(this.exercicio, 5, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// jun
			resumoContasMovimento.setJun(
					totalmes(this.exercicio, 6, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// jul
			resumoContasMovimento.setJul(
					totalmes(this.exercicio, 7, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// ago 8
			resumoContasMovimento.setAgo(
					totalmes(this.exercicio, 8, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// set
			resumoContasMovimento.setSet(
					totalmes(this.exercicio, 9, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// out
			resumoContasMovimento.setOut(
					totalmes(this.exercicio, 10, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// nov
			resumoContasMovimento.setNov(
					totalmes(this.exercicio, 11, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			// dez
			resumoContasMovimento.setDez(
					totalmes(this.exercicio, 12, StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId()));

			contasMovimento.add(resumoContasMovimento);

		}
		return contasMovimento;
	}

	public List<ResumoContasMovimento> resumocontasrecebersexercico() {

		contasMovimento = new ArrayList<ResumoContasMovimento>();
		double valoresAnterio = 0;
		List<Integer> movimentosf = faturaRepository.findbyano(TipoMovimentoEnum.entradaContrato.getDescricao());

		for (Integer resumoInterface : movimentosf) {
			valoresAnterio = 0;
			ResumoContasMovimento resumoContasMovimento = new ResumoContasMovimento();
			// resumoContasPagar.setId((Integer) resumoInterface[0]);
			// resumoContasPagar.setHistorico((String) resumoInterface[1]);
			this.exercicio = resumoInterface;
			resumoContasMovimento.setExercicio(this.exercicio);

			// ExercicoAtual
			valoresAnterio = 0;
			try {
				valoresAnterio = (double) faturaRepository.totalAnoByExercicio(this.exercicio,
						StatusActiv.ABERTO.getDescricao(), TipoMovimentoEnum.entradaContrato.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			resumoContasMovimento.setSaldoExercioAtual(valoresAnterio);

			// jan

			resumoContasMovimento.setJan(totalmesByExercicio(this.exercicio, 1, StatusActiv.ABERTO.getDescricao()));

			// fev

			resumoContasMovimento.setFev(totalmesByExercicio(this.exercicio, 2, StatusActiv.ABERTO.getDescricao()));

			// marv
			resumoContasMovimento.setMar(totalmesByExercicio(this.exercicio, 3, StatusActiv.ABERTO.getDescricao()));

			// abl
			resumoContasMovimento.setAbl(totalmesByExercicio(this.exercicio, 4, StatusActiv.ABERTO.getDescricao()));

			// Maio
			resumoContasMovimento.setMai(totalmesByExercicio(this.exercicio, 5, StatusActiv.ABERTO.getDescricao()));

			// jun
			resumoContasMovimento.setJun(totalmesByExercicio(this.exercicio, 6, StatusActiv.ABERTO.getDescricao()));

			// jul
			resumoContasMovimento.setJul(totalmesByExercicio(this.exercicio, 7, StatusActiv.ABERTO.getDescricao()));

			// ago 8
			resumoContasMovimento.setAgo(totalmesByExercicio(this.exercicio, 8, StatusActiv.ABERTO.getDescricao()));

			// set
			resumoContasMovimento.setSet(totalmesByExercicio(this.exercicio, 9, StatusActiv.ABERTO.getDescricao()));

			// out
			resumoContasMovimento.setOut(totalmesByExercicio(this.exercicio, 10, StatusActiv.ABERTO.getDescricao()));

			// nov
			resumoContasMovimento.setNov(totalmesByExercicio(this.exercicio, 11, StatusActiv.ABERTO.getDescricao()));

			// dez
			resumoContasMovimento.setDez(totalmesByExercicio(this.exercicio, 12, StatusActiv.ABERTO.getDescricao()));

			contasMovimento.add(resumoContasMovimento);

		}
		return contasMovimento;
	}

	public List<Integer> exerciciosAbert() {
		return faturaRepository.findbyano(TipoMovimentoEnum.entradaContrato.getDescricao());
	}

	public double totalReceber() {
		SimpleDateFormat ano = new SimpleDateFormat("yyyy");
		int exercicio = Integer.valueOf(ano.format(new Date()));
		contasMovimento = resumoContasRecebers(exercicio);
		double total = 0;
		for (ResumoContasMovimento resumoContasMovimento : contasMovimento) {
			total += resumoContasMovimento.getTotal();
		}
		return total;
	}

	public List<Events> createEvent() {
		List<Events> listevents = new ArrayList<Events>();
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		for (Fatura fatura : faturaRepository.findAllByStatusAndTipomovimento(StatusActiv.ABERTO.getDescricao(),
				TipoMovimentoEnum.entradaContrato.getDescricao())) {
			String strDate = sm.format(fatura.getDataVencimento());
			Events events = new Events(strDate, String.valueOf(fatura.getId()), fatura.getName(), "blue");
			listevents.add(events);
		}

		return listevents;
	}

	public void quitarFatura(FaturaContratoDto objDto) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Fatura obj = faturaRepository.findById(objDto.getId()).get();
		// List<FichaLeitura>
		// fichaLeitura=fichaLeituraRepository.findAllByFaturaId(obj.getId()) ;

		for (FichaLeituraDto fichaLeituraDto : objDto.getFichaLeitura()) {
			FichaLeitura fichaLeitura = fichaLeituraRepository.findById(fichaLeituraDto.getId()).get();
			fichaLeitura.setStatus(fichaLeituraDto.getStatus());
			fichaLeitura.setDataleitura(fichaLeituraDto.getDataleitura());
			fichaLeitura.setMedidorAtual(fichaLeituraDto.getMedidorAtual());
			Patrimonio patrimonio = patrimonioRepository.findById(fichaLeituraDto.getIdequipamento()).get();
			patrimonio.setMedidorContrato(fichaLeitura.getMedidorAtual());
			patrimonioRepository.save(patrimonio);
			fichaLeituraRepository.save(fichaLeitura);
		}
		MovimentoContrato movimentoContrato = find(obj.getMovimentoFinanceiro().getId());
		Contrato contrato = contratoService.find(movimentoContrato.getContrato().getId());
		contrato.setValor(objDto.getValor());

		List<AgregadoFinanceiroDto> valoresContrato = new ArrayList<>();
		AgregadoFinanceiroDto agregadoFinanceiro = new AgregadoFinanceiroDto(
				contrato.getFinanceiroContrato().getCentrocusto().getId(),
				contrato.getFinanceiroContrato().getCentrocusto().getName(),
				contrato.getFinanceiroContrato().getPercentualComplamento(), objDto.getValor());
		valoresContrato.add(agregadoFinanceiro);

		for (AgregadoFinanceiro financeiro : contrato.getFinanceiroContrato().getAgregadofinanceiros()) {
			agregadoFinanceiro = new AgregadoFinanceiroDto(financeiro.getCentrocusto().getId(),
					financeiro.getCentrocusto().getName(), financeiro.getPercentual(), objDto.getValor());
			valoresContrato.add(agregadoFinanceiro);
		}

		/*
		 * validação 1 - saldo em conta; 2- saldo no centro de custo;
		 */

		// 1 - saldo em conta;
		ContasBanco banco = bancoRepository.findById(objDto.getIdbanco()).get();
		if (banco.getSaldo() < objDto.getValor()) {
			throw new AuthorizationException("Saldo Insuficiente em banco");
		} else {
			banco.setSaldo(banco.getSaldo() + obj.getTotal());
			banco = bancoRepository.save(banco);
		}
		// 2- saldo no centro de custo

		for (AgregadoFinanceiroDto agregadoFinanceiroDto : valoresContrato) {
			CentroCusto centroCusto = centroCustoRepository.findById(agregadoFinanceiroDto.getId()).get();
			centroCusto.setSaldo(centroCusto.getSaldo() + agregadoFinanceiroDto.getValor());
			centroCusto.setSaldoReceber(centroCusto.getSaldoReceber() - agregadoFinanceiroDto.getValor());
			centroCusto = centroCustoRepository.save(centroCusto);
		}

		obj.setDataQuitacao(objDto.getDataquitacao());
		obj.setBancopagador(banco);
		obj.setFormapagamento(objDto.getFormapagamento());

		obj.setStatus(StatusActiv.QUIT.getDescricao());
		obj = faturaRepository.save(obj);
		movimentoContrato = find(obj.getMovimentoFinanceiro().getId());
		if (movimentoContrato.getFaturasAberto().size() == 0) {
			movimentoContrato.setStatus(StatusActiv.QUIT.getDescricao());
			movimentoContrato = repo.save(movimentoContrato);
		}

	}

	public FaturaContratoDto faturaContratoDto(Integer id) {
		Fatura fatura = faturaRepository.findById(id).get();
		List<FichaLeitura> fichaLeitura = fichaLeituraRepository.findAllByFaturaId(id);

		FaturaContratoDto contratoDto = new FaturaContratoDto(fatura);
		double valor = 0;
		for (FichaLeitura fichaLeitura2 : fichaLeitura) {
			FichaLeituraDto fichaLeituraDto = new FichaLeituraDto(fichaLeitura2);
			contratoDto.getFichaLeitura().add(fichaLeituraDto);

		}

		return contratoDto;
	}

}
