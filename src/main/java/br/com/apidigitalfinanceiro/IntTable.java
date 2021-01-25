package br.com.apidigitalfinanceiro;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.apidigitalfinanceiro.config.services.UserDetailsServiceImpl;
import br.com.apidigitalfinanceiro.domain.CentroCusto;
import br.com.apidigitalfinanceiro.domain.Clientes;
import br.com.apidigitalfinanceiro.domain.ContasBanco;
import br.com.apidigitalfinanceiro.domain.Contrato;
import br.com.apidigitalfinanceiro.domain.Empresas;
import br.com.apidigitalfinanceiro.domain.EnderecoClientes;
import br.com.apidigitalfinanceiro.domain.EnderecoEmpresas;
import br.com.apidigitalfinanceiro.domain.EnderecoFuncionarios;
import br.com.apidigitalfinanceiro.domain.Funcionarios;
import br.com.apidigitalfinanceiro.domain.GrupoFinanceiroContrato;
import br.com.apidigitalfinanceiro.domain.HistoricoPadraoSaida;
import br.com.apidigitalfinanceiro.domain.Modelo;
import br.com.apidigitalfinanceiro.domain.MovimentoSaida;
import br.com.apidigitalfinanceiro.domain.Patrimonio;
import br.com.apidigitalfinanceiro.enuns.SimNaoEnum;
import br.com.apidigitalfinanceiro.enuns.StatusActiv;
import br.com.apidigitalfinanceiro.enuns.TipoPessoaEnum;
import br.com.apidigitalfinanceiro.repository.CentroCustoRepository;
import br.com.apidigitalfinanceiro.repository.ClientesRepository;
import br.com.apidigitalfinanceiro.repository.ContasBancoRepository;
import br.com.apidigitalfinanceiro.repository.ContratoRepository;
import br.com.apidigitalfinanceiro.repository.EmpresasRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoClientesRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoEmpreRepository;
import br.com.apidigitalfinanceiro.repository.EnderecoFuncRepository;
import br.com.apidigitalfinanceiro.repository.FuncionariosRepository;
import br.com.apidigitalfinanceiro.repository.GrupoFinanceiroContratoRepository;
import br.com.apidigitalfinanceiro.repository.HistoricoPadraoSaidaRepository;
import br.com.apidigitalfinanceiro.repository.ModeloRepository;
import br.com.apidigitalfinanceiro.repository.PatrimonioRepository;
import br.com.apidigitalfinanceiro.services.MovimentoSaidaServices; 
 

@Service
public class IntTable {

	@Autowired
	ContasBancoRepository bancoRepository;
	
	@Autowired
	EnderecoFuncRepository enderecoRepository;

	@Autowired
	FuncionariosRepository funcionariosRepository;

	@Autowired
	EnderecoEmpreRepository enderecoEmpreRepository;

	@Autowired
	EmpresasRepository empresasRepository;

	@Autowired
	PatrimonioRepository patrimonioRepository;

	@Autowired
	ModeloRepository modeloRepository;

	@Autowired
	ClientesRepository clientesRepository;

	@Autowired
	EnderecoClientesRepository enderecoClientesRepository;

	@Autowired
	CentroCustoRepository centroCustoRepository;

	@Autowired
	MovimentoSaidaServices movimentoSaidaServices;

	@Autowired
	HistoricoPadraoSaidaRepository historicoPadraoSaidaRepository;

	@Autowired
	GrupoFinanceiroContratoRepository grupoFinanceiroContratoRepository;

	@Autowired
	ContratoRepository contratoService;

	@Autowired
	UserDetailsServiceImpl impl;

	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public void intTable() {

		Empresas e = new Empresas();
		e.setName("PAULA ANDREA GOMES MACEDO");
		e.setCpfOnCnpj("10380969000199");
		e.setAvatar("HEx1DWBKyf.png");
		e.setEmail("marcelo_macedo01@hotmail.com");
		e.setStatus(StatusActiv.ATIVO.getDescricao());
		e = empresasRepository.save(e);

		EnderecoEmpresas ed = new EnderecoEmpresas("R DELMIRO GOUVEIA", "63.050-216", "SALESIANOS", "JUAZEIRO DO NORTE",
				"1074", "", "CE", "ENDERECO PRINCIPAL", e);
		ed.setZipCode("63046460");
		ed.setPessoas(e);
		enderecoEmpreRepository.save(ed);
		e.setEnderecoPrincipal(ed);

		e = empresasRepository.save(e);
        
		ContasBanco banco=new ContasBanco();
		banco.setSaldo(15000);
		bancoRepository.save(banco);
		
		EnderecoFuncionarios endereco = new EnderecoFuncionarios();
		Funcionarios f = new Funcionarios();
		f.setTipo(TipoPessoaEnum.Funcionario.getDescricao());
		f.setName("M");
		f.setAvatar("HEx1DWBKyf.png");
		f.setEmpresa(e);
		java.util.List<String> list = new LinkedList<String>();
		list.add("Administrador Geral");
		f.setRolers("Administrador Geral");
		f.setEmail("marcelo_macedo01@hotmail.com");
		f.setStatus(StatusActiv.ATIVO.getDescricao());
		f.setSenha(encoder().encode("123456"));
		f = funcionariosRepository.save(f);
		endereco.setPessoas(f);
		endereco = enderecoRepository.save(endereco);
		f.setEnderecoPrincipal(endereco);
		funcionariosRepository.save(f);
		;

		EnderecoClientes ec = new EnderecoClientes();
		Clientes c = new Clientes();
		c.setName("Cliente1");
		c.setAvatar("HEx1DWBKyf.png");
		c.setEmpresa(e);

		c.setEmail("marcelo_macedo01@hotmail.com");
		c.setStatus(StatusActiv.ATIVO.getDescricao());
		c.setSenha(encoder().encode("123456"));
		c = clientesRepository.save(c);
		ec.setPessoas(c);
		ec = enderecoClientesRepository.save(ec);
		c.setEnderecoPrincipal(ec);
		clientesRepository.save(c);

		impl.loadUserByUsername(c.getEmail());

		Modelo m = new Modelo("XERX X", "0Llgv2MxSy.jpg");
		m = modeloRepository.save(m);
		m = new Modelo("XERX X1", "0Llgv2MxSy.jpg");
		m = modeloRepository.save(m);
		m = new Modelo("XERX X2", "0Llgv2MxSy.jpg");
		m = modeloRepository.save(m);

		Patrimonio pat = new Patrimonio();
		pat.setAvatar("0Llgv2MxSy.jpg");
		pat.setImagepatrimonio("fWXDA3oXVU.jpg");
		pat.setModelo(m);
		pat.setStatus(StatusActiv.ATIVO.getDescricao());
		pat.setStatuslocacao(SimNaoEnum.Nao.getDescricao());
		pat.setName("new Patrimonio");
		patrimonioRepository.save(pat);

		CentroCusto centroCusto = new CentroCusto();
		centroCusto.setName("Centro1");
		centroCusto.setSaldo(12000.112);
		centroCustoRepository.save(centroCusto);

		GrupoFinanceiroContrato financeiroContrato = new GrupoFinanceiroContrato();
		financeiroContrato.setName("financeiroContrato");
		financeiroContrato.setCentrocusto(centroCusto);// entroCusto.setSaldo(120.112);
		grupoFinanceiroContratoRepository.save(financeiroContrato);

		Contrato contrato = new Contrato();
		contrato.setCliente(c);
		contrato.setFinanceiroContrato(financeiroContrato);
		contrato.setName("contrato 1");
		contrato.setImageContrato("5UkuhiWG1v.pdf");
		contrato.setIsFranquia("Não");
		contrato.setValor(1000);
		contrato.setStatus(StatusActiv.ABERTO.getDescricao());
		contrato = contratoService.save(contrato);
		//contratoService.gerarparcelascontrato(contrato.getId());

		HistoricoPadraoSaida historicoPadraoSaida = new HistoricoPadraoSaida();
		historicoPadraoSaida.setName("Coelce");
		historicoPadraoSaida.setCentrocusto(centroCusto);// entroCusto.setSaldo(120.112);
		historicoPadraoSaidaRepository.save(historicoPadraoSaida);

		// MovimentoSaida
		MovimentoSaida movimentoSaida = new MovimentoSaida(12, historicoPadraoSaida, 100);
		movimentoSaida.setStatus(StatusActiv.ABERTO.getDescricao());
		movimentoSaida = movimentoSaidaServices.repo().save(movimentoSaida);
		movimentoSaida = movimentoSaidaServices.gerarparcelasCPagar(movimentoSaida.getId());
		

		historicoPadraoSaida = new HistoricoPadraoSaida();
		historicoPadraoSaida.setName("Telefone");
		historicoPadraoSaida.setCentrocusto(centroCusto);// entroCusto.setSaldo(120.112);
		historicoPadraoSaidaRepository.save(historicoPadraoSaida);
		movimentoSaida = new MovimentoSaida(12, historicoPadraoSaida, 1200);
		movimentoSaida.setStatus(StatusActiv.ABERTO.getDescricao());
		movimentoSaida = movimentoSaidaServices.repo().save(movimentoSaida);
		movimentoSaida = movimentoSaidaServices.gerarparcelasCPagar(movimentoSaida.getId());

		historicoPadraoSaida = new HistoricoPadraoSaida();
		historicoPadraoSaida.setName("Cagece");
		historicoPadraoSaida.setCentrocusto(centroCusto);// entroCusto.setSaldo(120.112);
		historicoPadraoSaidaRepository.save(historicoPadraoSaida);
		movimentoSaida = new MovimentoSaida(12, historicoPadraoSaida, 1550);
		movimentoSaida = movimentoSaidaServices.repo().save(movimentoSaida);
		movimentoSaida = movimentoSaidaServices.gerarparcelasCPagar(movimentoSaida.getId());

		historicoPadraoSaida = new HistoricoPadraoSaida();
		historicoPadraoSaida.setName("Folha");
		historicoPadraoSaida.setCentrocusto(centroCusto);// entroCusto.setSaldo(120.112);
		historicoPadraoSaidaRepository.save(historicoPadraoSaida);
		movimentoSaida = new MovimentoSaida(12, historicoPadraoSaida, 120);
		movimentoSaida = movimentoSaidaServices.repo().save(movimentoSaida);
		movimentoSaida = movimentoSaidaServices.gerarparcelasCPagar(movimentoSaida.getId());

		historicoPadraoSaida = new HistoricoPadraoSaida();
		historicoPadraoSaida.setName("Centro Material");
		historicoPadraoSaida.setCentrocusto(centroCusto);// entroCusto.setSaldo(120.112);
		historicoPadraoSaidaRepository.save(historicoPadraoSaida);
		movimentoSaida = new MovimentoSaida(12, historicoPadraoSaida, 1220);
		movimentoSaida = movimentoSaidaServices.repo().save(movimentoSaida);
		movimentoSaida = movimentoSaidaServices.gerarparcelasCPagar(movimentoSaida.getId());

		historicoPadraoSaida = new HistoricoPadraoSaida();
		historicoPadraoSaida.setName("Cartão Santander");
		historicoPadraoSaida.setCentrocusto(centroCusto);// entroCusto.setSaldo(120.112);
		historicoPadraoSaidaRepository.save(historicoPadraoSaida);
		movimentoSaida = new MovimentoSaida(12, historicoPadraoSaida, 1220);
		movimentoSaida.setName("Compra Cilindro ML 10/01/2021");
		movimentoSaida = movimentoSaidaServices.repo().save(movimentoSaida);
		movimentoSaida = movimentoSaidaServices.gerarparcelasCPagar(movimentoSaida.getId());

		movimentoSaida = new MovimentoSaida(12, historicoPadraoSaida, 7220);
		movimentoSaida.setName("Compra OverPrint NF 202020 10/01/2021");
		movimentoSaida = movimentoSaidaServices.repo().save(movimentoSaida);
		movimentoSaida = movimentoSaidaServices.gerarparcelasCPagar(movimentoSaida.getId());

	}
}
