package br.com.apidigitalfinanceiro.services;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.apidigitalfinanceiro.domain.AgregadoFinanceiro;
import br.com.apidigitalfinanceiro.domain.GrupoFinanceiro;
import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroContrato;
import br.com.apidigitalfinanceiro.repository.AgregadoFinanceiroRepository;

public class GrupoFinanceiroService<T extends GrupoFinanceiro> extends ServiceImpl<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	AgregadoFinanceiroRepository agregadoFinanceiroRepository;

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getClasse() {
		Class<T> classe = null;
		try {
			Class<T> class1 = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];

			classe = class1;
		} catch (Exception e) {
		}
		return classe;
	}

	@Override
	public T update(T obj) {
		if (obj.getAgregadofinanceiros() != null) {
			for (AgregadoFinanceiro agregadoFinanceiro : obj.getAgregadofinanceiros()) {
				if (agregadoFinanceiro.getGrupoFinanceiro() == null)
					agregadoFinanceiro.setGrupoFinanceiro(new GrupoFinanceiroContrato());
				agregadoFinanceiro.getGrupoFinanceiro().setId(obj.getId());
			}
		}
		if (obj.getAgregadofinanceiros().size() > 0) {
			agregadoFinanceiroRepository.saveAll(obj.getAgregadofinanceiros());
		}
		obj = super.update(obj);

		return obj;
	}

	@Override
	public T insert(T obj) {
		obj = super.insert(obj);
		if (obj.getAgregadofinanceiros() != null) {
			for (AgregadoFinanceiro agregadoFinanceiro : obj.getAgregadofinanceiros()) {
				if (agregadoFinanceiro.getGrupoFinanceiro() == null)
					agregadoFinanceiro.setGrupoFinanceiro(new GrupoFinanceiroContrato());
				agregadoFinanceiro.getGrupoFinanceiro().setId(obj.getId());
			}
		}
		if (obj.getAgregadofinanceiros().size() > 0) {
			agregadoFinanceiroRepository.saveAll(obj.getAgregadofinanceiros());
		}
		return obj;
	}

	public void deleteAgregado(Integer id) {
		agregadoFinanceiroRepository.deleteById(id);
	}

	public T clonar(T obj) {
		List<AgregadoFinanceiro> agregadoFinanceiros = new ArrayList<AgregadoFinanceiro>();

		if (obj.getAgregadofinanceiros() != null) {
			for (AgregadoFinanceiro agregadoFinanceiro : obj.getAgregadofinanceiros()) {
				agregadoFinanceiro.getGrupoFinanceiro().setId(null);
				agregadoFinanceiros.add(agregadoFinanceiro);
			}
		}
		obj = super.update(obj);
		obj.setAgregadofinanceiros(agregadoFinanceiros);
		if (obj.getAgregadofinanceiros() != null) {
			for (AgregadoFinanceiro agregadoFinanceiro : obj.getAgregadofinanceiros()) {
				if (agregadoFinanceiro.getGrupoFinanceiro() == null)
					agregadoFinanceiro.setGrupoFinanceiro(new GrupoFinanceiroContrato());
				agregadoFinanceiro.getGrupoFinanceiro().setId(obj.getId());
			}
		}

		if (obj.getAgregadofinanceiros().size() > 0) {
			agregadoFinanceiroRepository.saveAll(obj.getAgregadofinanceiros());
		}
		 

		return obj;
	}
}
