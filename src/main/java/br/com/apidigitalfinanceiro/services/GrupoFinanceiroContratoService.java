package br.com.apidigitalfinanceiro.services;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroContrato;
import br.com.apidigitalfinanceiro.repository.GrupoFinanceiroContratoRepository;

@Service
public class GrupoFinanceiroContratoService  extends GrupoFinanceiroService<GrupoFinanceiroContrato>{

	private static final long serialVersionUID = 1L;

	@Autowired
	GrupoFinanceiroContratoRepository repo;

	@Override
	public JpaRepository<GrupoFinanceiroContrato, Integer> repo() {
		return repo;
	}

	/*@Autowired
	AgregadoFinanceiroRepository agregadoFinanceiroRepository;

	@Override
	public GrupoFinanceiroContrato update(GrupoFinanceiroContrato obj) {
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
	public GrupoFinanceiroContrato insert(GrupoFinanceiroContrato obj) {
		// TODO Auto-generated method stub
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
	*/
}
