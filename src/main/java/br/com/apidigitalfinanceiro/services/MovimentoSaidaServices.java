package br.com.apidigitalfinanceiro.services;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.apidigitalfinanceiro.config.security.UserSS;
import br.com.apidigitalfinanceiro.config.services.UserService;
import br.com.apidigitalfinanceiro.domain.CentroCusto;
import br.com.apidigitalfinanceiro.domain.ContasBanco;
import br.com.apidigitalfinanceiro.domain.Fatura;
import br.com.apidigitalfinanceiro.domain.HistoricoPadraoSaida;
import br.com.apidigitalfinanceiro.domain.MovimentoSaida;
import br.com.apidigitalfinanceiro.dto.Events;
import br.com.apidigitalfinanceiro.dto.FaturaDto;
import br.com.apidigitalfinanceiro.dto.MovimentoFinanceiroDto;
import br.com.apidigitalfinanceiro.dto.ResumoContasMovimento;
import br.com.apidigitalfinanceiro.enuns.NaturezaMovimentoEnum;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.enuns.TipoMovimentoEnum;
import br.com.apidigitalfinanceiro.repository.CentroCustoRepository;
import br.com.apidigitalfinanceiro.repository.ContasBancoRepository;
import br.com.apidigitalfinanceiro.repository.FaturaRepository;
import br.com.apidigitalfinanceiro.repository.FuncionariosRepository;
import br.com.apidigitalfinanceiro.repository.HistoricoPadraoSaidaRepository;
import br.com.apidigitalfinanceiro.repository.MovimentoSaidaRepository;
import br.com.apidigitalfinanceiro.service.security.exceptions.AuthorizationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MovimentoSaidaServices extends ServiceImpl<MovimentoSaida> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	FaturaRepository faturaRepository;

	@Autowired
	FuncionariosRepository funcionariosRepository;

	@Autowired
	MovimentoSaidaRepository repo;

	@Autowired
	ContasBancoRepository bancoRepository;

	@Autowired
	HistoricoPadraoSaidaRepository saidaRepository;

	@Autowired
	CentroCustoRepository centroCustoRepository;

	@Override
	public JpaRepository<MovimentoSaida, Integer> repo() {
		return repo;
	}

	private Integer exercicio;

	@PostConstruct
	public void inicio() {
		SimpleDateFormat ano = new SimpleDateFormat("yyyy");
		exercicio = Integer.valueOf(ano.format(new Date()));
	}

	@Override
	public List<MovimentoSaida> findAll() {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		List<MovimentoSaida> find = repo.findAll();

		return find;
	}

	private List<ResumoContasMovimento> contasPagars = new ArrayList<ResumoContasMovimento>();

	@SuppressWarnings("unused")
	private double totalmes(int exercico, int mes, String status, int id) {
		double valoresAnterio = 0;
		try {
			valoresAnterio = (double) faturaRepository.totalmes(exercico, mes, status, id,
					TipoMovimentoEnum.Saida.getDescricao());
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
					TipoMovimentoEnum.Saida.getDescricao());
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
					TipoMovimentoEnum.Saida.getDescricao());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valoresAnterio;
	}

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
			valoresAnterio = (double) faturaRepository.totalmovimentoFinanceiroexercicioPosterior(this.exercicio,
					StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId());
		} catch (Exception e) {
			// TODO: handle exception
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

	public List<ResumoContasMovimento> resumoContasMovimentos(int exercico) {
		if (exercico > 0)
			this.exercicio = exercico;
		contasPagars = new ArrayList<ResumoContasMovimento>();
		double valoresAnterio = 0;
		List<Object[]> movimentosf = repo.movimentosByFaturas(StatusActiv.ABERTO.getDescricao());

		for (Object[] resumoInterface : movimentosf) {
			valoresAnterio = 0;
			ResumoContasMovimento resumoContasMovimento = new ResumoContasMovimento();
			resumoContasMovimento.setId((Integer) resumoInterface[0]);
			resumoContasMovimento.setHistorico((String) resumoInterface[1]);

			// lista movimentos
			List<ResumoContasMovimento> listahst = new ArrayList<ResumoContasMovimento>();
			List<Object[]> listhst = repo.movimentosByFaturasAndHistorico(StatusActiv.ABERTO.getDescricao(),
					(Integer) resumoInterface[0]);
			for (Object[] obj : listhst) {
				listahst.add(addlistahst(obj));
			}
			resumoContasMovimento.setListahst(listahst);
			resumoContasMovimento.setExercicio(this.exercicio);
			// ExercicoAnterior
			try {
				valoresAnterio = (double) faturaRepository.totalAnterior(this.exercicio,
						StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId(),
						TipoMovimentoEnum.Saida.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			resumoContasMovimento.setSaldoanterior(valoresAnterio);

			// ExercicoAtual
			valoresAnterio = 0;
			try {
				valoresAnterio = (double) faturaRepository.totalexercicioAtual(this.exercicio,
						StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId(),
						TipoMovimentoEnum.Saida.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			resumoContasMovimento.setSaldoExercioAtual(valoresAnterio);

			// ExercicoPostrior
			valoresAnterio = 0;
			try {
				valoresAnterio = (double) faturaRepository.totalexercicioPosterior(this.exercicio,
						StatusActiv.ABERTO.getDescricao(), resumoContasMovimento.getId(),
						TipoMovimentoEnum.Saida.getDescricao());
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

			contasPagars.add(resumoContasMovimento);

		}
		return contasPagars;
	}

	public List<ResumoContasMovimento> resumocontaspagarsexercico() {

		contasPagars = new ArrayList<ResumoContasMovimento>();
		double valoresAnterio = 0;
		List<Integer> movimentosf = faturaRepository.findbyano(TipoMovimentoEnum.Saida.getDescricao());

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
						StatusActiv.ABERTO.getDescricao(), TipoMovimentoEnum.Saida.getDescricao());
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

			contasPagars.add(resumoContasMovimento);

		}
		return contasPagars;
	}

	public void excluirParcelas(MovimentoSaida movement) {

		// exclusão ficha leitura

		// exclusão faturas abertas banco de dados
		faturaRepository.deleteAll(movement.getFaturasAberto());

		// exclusão faturas abertas lista Movimento....................

	}

	public List<Integer> maxparcela(MovimentoSaida movement, int maxparcela) {
		List<Integer> retorno = new LinkedList<Integer>();
		int maxparcelaquit = 1;
		int cont;
		for (int i = 0; i < maxparcela; i++) {
			boolean condition = false;
			while (!condition) {
				cont = movement.getFaturasQuit().size();
				for (Fatura itemmovimento : movement.getFaturasQuit()) {
					cont--;
					if (maxparcelaquit == itemmovimento.getParcela()) {
						condition = false;
						cont = movement.getFaturasQuit().size();
					}
				}
				if (cont == 0) {
					condition = true;
					retorno.add(maxparcelaquit);
					++maxparcelaquit;
				} else {
					++maxparcelaquit;
				}
			}
		}
		return retorno;
	}

	@SuppressWarnings("unused")
	public MovimentoSaida gerarparcelasCPagar(Integer id) {
		MovimentoSaida movement = new MovimentoSaida();
		movement = repo.findById(id).get();
		if (movement.getDataMovimento() == null)
			movement.setDataMovimento(new Date());
		movement.setTipomovimento(TipoMovimentoEnum.Saida.getDescricao());

		if (movement.getStatus().equals(StatusActiv.ABERTO.getDescricao())) {
			movement.setTipomovimento(TipoMovimentoEnum.Saida.getDescricao());

			DateTime plusPeriod = new DateTime(movement.getDataVencimento());// dt.plus(Period.months(1));
			DateTime plusPeriodleitura = new DateTime();
			// DateTime plusDuration = dt.plus(new Duration(24L * 60L * 60L *
			// 1000L));
			Fatura itemmovimento;
			int j = movement.getParcela() - movement.getFaturasQuit().size();
			List<Integer> indiceparcalas = new LinkedList<Integer>();
			indiceparcalas = maxparcela(movement, j);
			double valr1 = movement.getValor();// getValorAberto() / j;
			HistoricoPadraoSaida historicoPadraoSaida = saidaRepository.findById(movement.getHistorico().getId()).get();
			CentroCusto centroCusto = centroCustoRepository.findById(historicoPadraoSaida.getCentrocusto().getId())
					.get();

			for (int i = 0; i < j; i++) {
				itemmovimento = new Fatura();

				int dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
				int DaysToAdd;
				// se final de semana
				if (dayOfWeekEndDateNumber == 6 || dayOfWeekEndDateNumber == 7) {
					DaysToAdd = 8 - dayOfWeekEndDateNumber;
					plusPeriod = plusPeriod.plusDays(DaysToAdd);
					dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
				}

				dayOfWeekEndDateNumber = Integer.valueOf(plusPeriodleitura.dayOfWeek().getAsString());

				itemmovimento.setName(movement.getName());
				itemmovimento.setParcela(indiceparcalas.get(i));
				itemmovimento.setValor(movement.getValor() / j);
				itemmovimento.setDataVencimento(plusPeriod.toDate());
				itemmovimento.setInstantCreation(new Date());
				itemmovimento.setStatus(StatusActiv.ABERTO.getDescricao());
				itemmovimento.setMovimentoFinanceiro(movement);
				itemmovimento.setHistorico(movement.getHistorico());
				itemmovimento.setNaturezamovimento(NaturezaMovimentoEnum.Saida.getDescricao());
				itemmovimento.setTipomovimento(TipoMovimentoEnum.Saida.getDescricao());
				itemmovimento.setDataMovimento(movement.getDataMovimento());
				itemmovimento = faturaRepository.save(itemmovimento);
				centroCusto.setSaldoPagar(centroCusto.getSaldoPagar() + (movement.getValor() / j));

				plusPeriod = plusPeriod.plus(org.joda.time.Period.months(1));
			}
			centroCusto = centroCustoRepository.save(centroCusto);
			return repo.save(movement);
		} else
			return movement;

	}

	public List<Integer> exerciciosAbert() {
		return faturaRepository.findbyano(TipoMovimentoEnum.Saida.getDescricao());
	}

	public double totalPagar() {
		SimpleDateFormat ano = new SimpleDateFormat("yyyy");
		int exercicio = Integer.valueOf(ano.format(new Date()));
		contasPagars = resumoContasMovimentos(exercicio);
		double total = 0;
		for (ResumoContasMovimento resumoContasMovimento : contasPagars) {
			total += resumoContasMovimento.getTotal();
		}
		return total;
	}

	public List<Events> createEvent() {
		List<Events> listevents = new ArrayList<Events>();
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		for (Fatura fatura : faturaRepository.findAllByStatusAndTipomovimento(StatusActiv.ABERTO.getDescricao(),
				TipoMovimentoEnum.Saida.getDescricao())) {
			String strDate = sm.format(fatura.getDataVencimento());
			Events events = new Events(strDate, String.valueOf(fatura.getId()), fatura.getName(), "red");
			listevents.add(events);
		}

		return listevents;
	}

	public void quitarFatura(FaturaDto objDto) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Fatura obj = faturaRepository.findById(objDto.getId()).get();
		if (obj.getStatus().equals(StatusActiv.QUIT.getDescricao())) {
			throw new AuthorizationException("Este paagamento já foi realizado");
		}
		obj.setDataQuitacao(objDto.getDataquitacao());
		obj.setDesconto(objDto.getDesconto());
		obj.setFuncionario(funcionariosRepository.findById(user.getId()).get());
		/*
		 * validação 1 - saldo em conta; 2- saldo no centro de custo;
		 */

		// 1 - saldo em conta;
		ContasBanco banco = bancoRepository.findById(objDto.getIdbanco()).get();
		if (banco.getSaldo() < obj.getTotal()) {
			throw new AuthorizationException("Saldo Insuficiente em banco");
		} else {
			banco.setSaldo(banco.getSaldo() - obj.getTotal());
			banco = bancoRepository.save(banco);
		}

		obj.setBancopagador(banco);
		obj.setNumerodocumento(objDto.getNumerodocumento());
		obj.setFormapagamento(objDto.getFormapagamento());
		obj.setMulta(objDto.getMulta());
		obj.setDesconto(objDto.getDesconto());
		// 2- saldo no centro de custo
		CentroCusto centroCusto = centroCustoRepository.findById(obj.getHistorico().getCentrocusto().getId()).get();
		if (centroCusto.getSaldo() < obj.getTotal()) {
			throw new AuthorizationException("Saldo Insuficiente no centro de custo - " + centroCusto.getName());
		} else {
			centroCusto.setSaldo(centroCusto.getSaldo() - obj.getTotal());
			centroCusto.setSaldoPagar(centroCusto.getSaldoPagar() - obj.getTotal());
			if (centroCusto.getSaldoPagar() < 0)
				centroCusto.setSaldoPagar(0);
			centroCusto = centroCustoRepository.save(centroCusto);
		}

		// String s = uploadProfilePicture(objDto.getFile(), objDto.getId(),
		// "imagemfatura");

		obj.setDataQuitacao(objDto.getDataquitacao());
		obj.setBancopagador(banco);
		obj.setFormapagamento(objDto.getFormapagamento());

		obj.setStatus(StatusActiv.QUIT.getDescricao());
		obj = faturaRepository.save(obj);

		MovimentoSaida movimentoSaida = repo.findById(obj.getMovimentoFinanceiro().getId()).get();
		if (movimentoSaida.getFaturasAberto().size() == 0) {
			movimentoSaida.setStatus(StatusActiv.QUIT.getDescricao());
			movimentoSaida = repo.save(movimentoSaida);
		}
		obj.setStatus(StatusActiv.QUIT.getDescricao());
		obj = faturaRepository.save(obj);
	}

	public FaturaDto faturaDto(Integer id) {
		FaturaDto faturaDto = new FaturaDto(faturaRepository.findById(id).get());
		return faturaDto;
	}

	@Override
	public String uploadProfilePicture(MultipartFile file, Integer id, String fieldname) {
		Field fldfonte = null;
		Object value = null;

		String filename = "";
		Fatura obj = faturaRepository.findById(id).get();
		try {

			fldfonte = Fatura.class.getDeclaredField(fieldname);

			fldfonte.setAccessible(true);
			value = fldfonte.get(obj);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {

			e1.printStackTrace();
		}
		fldfonte.setAccessible(true);

		try {
			filename = filesService._uploadProfilePicture(file, (String) value);

		} catch (Exception e) {
			filename = filesService._uploadProfilePicture(file, "");
		}
		// System.out.println(filesService.downloadFile(filename));

		try {
			fldfonte.set(obj, filename);
		} catch (IllegalArgumentException | IllegalAccessException e) {

			e.printStackTrace();
		}
		faturaRepository.save(obj);

		try {
			return filesService.downloadFile(filename);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return fieldname;
	}

	@Override
	public MovimentoSaida insert(MovimentoSaida obj) {
		obj.setStatus(StatusActiv.ABERTO.getDescricao());
		obj.setTipomovimento(TipoMovimentoEnum.Saida.getDescricao());
		obj = super.insert(obj);
		obj = gerarparcelasCPagar(obj.getId());
		return obj;
	}

	public MovimentoFinanceiroDto editarMoviemnto(int id) {
		MovimentoSaida movimentoSaida = repo.findById(id).get();
		MovimentoFinanceiroDto financeiroDto = new MovimentoFinanceiroDto(movimentoSaida);
		for (Fatura fatura : movimentoSaida.getFaturasAberto()) {
			FaturaDto f = new FaturaDto();
			f = faturaDto(fatura.getId());
			financeiroDto.getFaturas().add(f);
		}
		return financeiroDto;
	}

}
