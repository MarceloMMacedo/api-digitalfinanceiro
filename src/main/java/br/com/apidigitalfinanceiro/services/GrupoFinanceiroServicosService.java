package br.com.apidigitalfinanceiro.services;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroServicos;
import br.com.apidigitalfinanceiro.repository.GrupoFinanceiroServicosRepository;

@Service
public class GrupoFinanceiroServicosService  extends GrupoFinanceiroService<GrupoFinanceiroServicos>{

	private static final long serialVersionUID = 1L;

	@Autowired
	GrupoFinanceiroServicosRepository repo;

	@Override
	public JpaRepository<GrupoFinanceiroServicos, Integer> repo() {
		return repo;
	}

	/*@Autowired
	AgregadoFinanceiroRepository agregadoFinanceiroRepository;

	@Override
	public GrupoFinanceiroServicos update(GrupoFinanceiroServicos obj) {
	if (obj.getAgregadofinanceiros() != null) {
			for (AgregadoFinanceiro agregadoFinanceiro : obj.getAgregadofinanceiros()) {
				if (agregadoFinanceiro.getGrupoFinanceiro() == null)
					agregadoFinanceiro.setGrupoFinanceiro(new GrupoFinanceiroServicos());
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
	public GrupoFinanceiroServicos insert(GrupoFinanceiroServicos obj) {
		// TODO Auto-generated method stub
		obj = super.insert(obj);
		if (obj.getAgregadofinanceiros() != null) {
			for (AgregadoFinanceiro agregadoFinanceiro : obj.getAgregadofinanceiros()) {
				if (agregadoFinanceiro.getGrupoFinanceiro() == null)
					agregadoFinanceiro.setGrupoFinanceiro(new GrupoFinanceiroServicos());
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
	*/
}
