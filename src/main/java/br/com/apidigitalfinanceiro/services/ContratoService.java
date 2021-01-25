package br.com.apidigitalfinanceiro.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.CentroCusto;
import br.com.apidigitalfinanceiro.domain.Clientes;
import br.com.apidigitalfinanceiro.domain.Contrato;
import br.com.apidigitalfinanceiro.domain.EquipamentosContrato;
import br.com.apidigitalfinanceiro.domain.Fatura;
import br.com.apidigitalfinanceiro.domain.FichaLeitura;
import br.com.apidigitalfinanceiro.domain.MovimentoContrato;
import br.com.apidigitalfinanceiro.domain.Patrimonio;
import br.com.apidigitalfinanceiro.dto.BaseDto;
import br.com.apidigitalfinanceiro.enuns.SimNaoEnum;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.enuns.TipoMovimentoEnum;
import br.com.apidigitalfinanceiro.repository.CentroCustoRepository;
import br.com.apidigitalfinanceiro.repository.ClientesRepository;
import br.com.apidigitalfinanceiro.repository.ContratoRepository;
import br.com.apidigitalfinanceiro.repository.EquipamentosContratoRepository;
import br.com.apidigitalfinanceiro.repository.FaturaRepository;
import br.com.apidigitalfinanceiro.repository.FichaLeituraRepository;
import br.com.apidigitalfinanceiro.repository.GrupoFinanceiroContratoRepository;
import br.com.apidigitalfinanceiro.repository.MovimentoContratoRepository;
import br.com.apidigitalfinanceiro.repository.PatrimonioRepository;
import br.com.apidigitalfinanceiro.utils.Extenso;

@Service
public class ContratoService extends ServiceImpl<Contrato> {

	private static final long serialVersionUID = 1L;

	@Autowired
	ContratoRepository repo;

	@Autowired
	EquipamentosContratoRepository equipamentosContratoRepository;

	@Autowired
	PatrimonioRepository patrimonioRepository;

	@Autowired
	FaturaRepository faturaRepository;

	@Autowired
	CentroCustoRepository centroCustoRepository;

	@Autowired
	MovimentoContratoRepository movimentoContratoRepository;

	@Autowired
	FichaLeituraRepository fichaLeituraRepository;

	@Autowired
	ClientesRepository clientesRepository;

	@Autowired
	GrupoFinanceiroContratoRepository contratoRepository;

	@Override
	public JpaRepository<Contrato, Integer> repo() {
		return repo;
	}

	@Override
	public List<Contrato> findAll() {
		return repo.findByStatus(StatusActiv.ABERTO.getDescricao());
	}

	@Override
	public Contrato find(Integer id) {
		return SetImgSingle("imageContrato", "imageContratoView", super.find(id));
	}

	@Override
	public List<BaseDto> findBaseAll() {
		List<BaseDto> baseDto = new ArrayList<>();
		for (Contrato contrato : findAll()) {
			baseDto.add(new BaseDto(contrato.getId(), contrato.getName(), contrato.getClientename(), null, null,
					new Extenso(contrato.getTotal()).DecimalFormat()));
		}
		return baseDto;
	}

	@Override
	public Contrato insert(Contrato obj) {
		obj.setStatus(StatusActiv.ABERTO.getDescricao());
		Clientes clientes = clientesRepository.findById(obj.getCliente().getId()).get();
		obj.setCliente(clientes);
		obj = super.insert(obj);
		if (obj.getEquipamentosContratos() != null) {
			for (EquipamentosContrato equipamentosContrato : obj.getEquipamentosContratos()) {
				if (equipamentosContrato.getContrato() == null) {
					equipamentosContrato.setContrato(new Contrato());
					equipamentosContrato.getContrato().setId(obj.getId());
				}

				equipamentosContrato.setEquipamento(
						patrimonioRepository.findById(equipamentosContrato.getEquipamento().getId()).get());

				equipamentosContrato.getEquipamento().setStatuslocacao(SimNaoEnum.Sim.getDescricao());

				patrimonioRepository.save(equipamentosContrato.getEquipamento());

			}
			equipamentosContratoRepository.saveAll(obj.getEquipamentosContratos());
		}
		return obj;
	}

	@Override
	public Contrato update(Contrato obj) {
		obj.setStatus(StatusActiv.ABERTO.getDescricao());
		Clientes clientes = clientesRepository.findById(obj.getCliente().getId()).get();
		obj.setCliente(clientes);
		if (obj.getEquipamentosContratos() != null) {
			for (EquipamentosContrato equipamentosContrato : obj.getEquipamentosContratos()) {
				if (equipamentosContrato.getContrato() == null) {
					equipamentosContrato.setContrato(new Contrato());
				}

				equipamentosContrato.setEquipamento(
						patrimonioRepository.findById(equipamentosContrato.getEquipamento().getId()).get());
				equipamentosContrato.getContrato().setId(obj.getId());
				equipamentosContrato.getEquipamento().setStatuslocacao(SimNaoEnum.Sim.getDescricao());

				patrimonioRepository.save(equipamentosContrato.getEquipamento());

			}
			equipamentosContratoRepository.saveAll(obj.getEquipamentosContratos());
		}
		return super.update(obj);
	}

	public Contrato clonar(Contrato obj) {
		obj.setStatus(StatusActiv.ABERTO.getDescricao());
		List<EquipamentosContrato> equipamentosContratos = new ArrayList<EquipamentosContrato>();

		if (obj.getEquipamentosContratos() != null) {
			for (EquipamentosContrato equipamentosContrato : obj.getEquipamentosContratos()) {
				equipamentosContrato.setId(null);
				equipamentosContratos.add(equipamentosContrato);
			}
		}

		obj = super.insert(obj);

		{
			for (EquipamentosContrato equipamentosContrato : equipamentosContratos) {
				if (equipamentosContrato.getContrato() == null) {
					equipamentosContrato.setContrato(new Contrato());
					equipamentosContrato.setContrato(obj);
				}
			}
			if (equipamentosContratos.size() > 0)
				equipamentosContratos = equipamentosContratoRepository.saveAll(equipamentosContratos);
		}
		obj.setEquipamentosContratos(equipamentosContratos);
		return obj;
	}

	public void deleteEquipamento(Integer id) {

		EquipamentosContrato equipamentosContrato = equipamentosContratoRepository.findById(id).get();
		equipamentosContrato.getEquipamento().setContrato(null);
		equipamentosContrato.getEquipamento().setStatuslocacao(SimNaoEnum.Nao.getDescricao());
		patrimonioRepository.save(equipamentosContrato.getEquipamento());

		equipamentosContratoRepository.deleteById(id);
	}

	public List<Patrimonio> equipamentos() {
		return patrimonioRepository.findByStatuslocacao(SimNaoEnum.Nao.getDescricao());
	}

	public void excluirParcelas(MovimentoContrato movement) {

		// exclusão ficha leitura

		// exclusão faturas abertas banco de dados
		faturaRepository.deleteAll(movement.getFaturasAberto());

		// exclusão faturas abertas lista Movimento....................

	}

	public MovimentoContrato findMovimentoContrato(int id) {

		MovimentoContrato obj = movimentoContratoRepository.findByContratoIdAndStatus(id,
				StatusActiv.ABERTO.getDescricao());
		if (obj == null) {
			Contrato contrato = new Contrato();
			contrato = repo.findById(id).get();
			obj = movimentoContratoRepository.save(new MovimentoContrato(id, contrato.getPeriodo()));
		}

		return obj;
	}

	public List<Integer> maxparcela(MovimentoContrato movement, int maxparcela) {
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

	public void gerarparcelascontrato1(int id, int idcliente, int idgrupofinanceiro) {
		Contrato contrato = repo().findById(id).get();
		System.out.println(contrato.getCliente().getName());
	}

	public void gerarparcelascontrato(int id, int idcliente, int idgrupofinanceiro) {

		// GrupoFinanceiroContrato financeiroContrato =
		// contratoRepository.findById(idgrupofinanceiro).get();

		// Clientes clientes = clientesRepository.findById(idcliente).get();
		Contrato contrato = repo.findById(id).get();
		// contrato.setCliente(clientes);
		// contrato.setFinanceiroContrato(financeiroContrato);
		repo.save(contrato);
		MovimentoContrato movement = movimentoContratoRepository.findByContratoIdAndStatus(id,
				StatusActiv.ABERTO.getDescricao());
		if (movement == null) {
			movement = new MovimentoContrato();
			movement.setDataMovimento(new Date());
			movement.setStatus(StatusActiv.ABERTO.getDescricao());
			movement.setTipomovimento(TipoMovimentoEnum.entradaContrato.getDescricao());
		}
		movement.setValor(contrato.getTotal());
		movement.setContrato(contrato);
		movement.setHistorico(contrato.getFinanceiroContrato());
		movement.setParcela(contrato.getPeriodo());

		movement.setName("Contrato Nº " + String.valueOf(id) + " Cliente: " + contrato.getClientename());
		movement = movimentoContratoRepository.save(movement);

		fichaLeituraRepository
				.deleteAll(fichaLeituraRepository.findByContratoIdAndStatus(id, StatusActiv.ABERTO.getDescricao()));
		excluirParcelas(movement);
		movement.setValor(contrato.getTotal());
		DateTime dt;
		DateTime dt12;

		//if (movement.getFaturasQuit().size() > 0) {

			//dt = new DateTime(movement.getFaturasQuit().get(0).getDataVencimento());
			dt = new DateTime();
			int ano = dt.getYear();
			int mes = dt.getMonthOfYear();
			dt = new DateTime(ano, mes, contrato.getDiaVencimento(), 8, 0); 
			dt12 = new DateTime(ano, mes, contrato.getDiaLeitura(), 8, 0); 

		/*} 
		else {
			dt = new DateTime();
			int ano = dt.getYear();
			int mes = dt.getMonthOfYear();
			dt12 = new DateTime(ano, mes, contrato.getDiaLeitura(), 8, 0);
		}*/
		/*DateTime dt1 = new DateTime(new Date());
		for (Fatura itemmovimento : movement.getFaturasQuit()) {
			dt1 = new DateTime(itemmovimento.getDataVencimento());
			if (dt1.isAfter(dt)) {
				dt = dt1;
			}
			if (dt1.isAfter(dt12)) {
				dt12 = dt1;
			}

		}
		dt1 = dt12;*/
		DateTime plusPeriod = new DateTime();
		DateTime plusPeriodleitura = new DateTime();
		Fatura itemmovimento;
		int j = movement.getParcela() - movement.getFaturasQuit().size();
		List<Integer> indiceparcalas = new LinkedList<Integer>();
		indiceparcalas = maxparcela(movement, j);
		double valr1 = contrato.getTotal();// getValorAberto() / j;

		List<FichaLeitura> fichas = new ArrayList<>();
		for (int i = 0; i < j; i++) {
			itemmovimento = new Fatura(movement, (movement.getValor() - movement.getValorAberto()));
			plusPeriod = dt.plus(org.joda.time.Period.months(i));
			plusPeriodleitura = dt12.plus(org.joda.time.Period.months(i));
			int dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
			int DaysToAdd;
			// se final de semana
			if (dayOfWeekEndDateNumber == 6 || dayOfWeekEndDateNumber == 7) {
				DaysToAdd = 8 - dayOfWeekEndDateNumber;
				plusPeriod = plusPeriod.plusDays(DaysToAdd);
				dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
			}

			dayOfWeekEndDateNumber = Integer.valueOf(plusPeriodleitura.dayOfWeek().getAsString());

			// se final de semana
			if (dayOfWeekEndDateNumber == 6 || dayOfWeekEndDateNumber == 7) {
				DaysToAdd = 8 - dayOfWeekEndDateNumber;
				plusPeriodleitura = plusPeriodleitura.plusDays(DaysToAdd);
				dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
			}

			itemmovimento.setParcela(indiceparcalas.get(i));
			itemmovimento.setValor(contrato.getTotal());
			itemmovimento.setDataVencimento(plusPeriod.toDate());
			itemmovimento.setDataleitura(plusPeriodleitura.toDate());
			itemmovimento.setInstantCreation(new Date());
			itemmovimento.setStatus(StatusActiv.ABERTO.getDescricao());
			itemmovimento.setMovimentoFinanceiro(movement);
			itemmovimento.setContrato(contrato);
			itemmovimento.setDataMovimento(movement.getDataMovimento());
			itemmovimento.setTipomovimento(TipoMovimentoEnum.entradaContrato.getDescricao());

			/*
			 * FichaLeitura fichaLeitura = new FichaLeitura(contrato, equipamentoContrato,
			 * itemmovimento.getDataleitura(), StatusActiv.ABERTO.getDescricao());
			 */

			CentroCusto centroCusto = centroCustoRepository.findById(movement.getHistorico().getCentrocusto().getId())
					.get();
			centroCusto.setSaldoReceber(centroCusto.getSaldoReceber() + (contrato.getTotal()));

			centroCustoRepository.save(centroCusto);
			itemmovimento = faturaRepository.save(itemmovimento);
			for (EquipamentosContrato equipamentoContrato : contrato.getEquipamentosContratos()) {
				fichas.add(new FichaLeitura(contrato, equipamentoContrato, itemmovimento.getDataleitura(),
						StatusActiv.ABERTO.getDescricao(), itemmovimento));
			}

		}
		fichaLeituraRepository.saveAll(fichas);

	}
}
