package br.com.apidigitalfinanceiro.services;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import br.com.apidigitalfinanceiro.config.security.UserSS;
import br.com.apidigitalfinanceiro.config.services.UserService;
import br.com.apidigitalfinanceiro.domain.EnderecoEmpresas;
import br.com.apidigitalfinanceiro.domain.intefaces.BaseContactInterface;
import br.com.apidigitalfinanceiro.domain.intefaces.BaseEnderecoInterface;
import br.com.apidigitalfinanceiro.domain.intefaces.BaseEntity;
import br.com.apidigitalfinanceiro.domain.intefaces.PessoaBaseInterface;
import br.com.apidigitalfinanceiro.dto.BaseDto;
import br.com.apidigitalfinanceiro.service.security.exceptions.AuthorizationException;

public class PessoaBaseService<T extends BaseEntity, BC extends BaseContactInterface, BE extends BaseEnderecoInterface, P extends PessoaBaseInterface<BC, BE>>
		extends ServiceImpl<T> {

	private static final long serialVersionUID = 1L;

	public JpaRepository<BE, Integer> getRepoEnd() {
		return null;
	}

	public JpaRepository<BC, Integer> getRepoContact() {
		return null;
	}

	 public JpaRepository<P, Integer> getRepo() {
		return null;
	} 

	public String getTipoPessoa() {
		return null;
	}

	P pessoa;

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
public JpaRepository<T, Integer> repo() {
	// TODO Auto-generated method stub
	return super.repo();
}
	/*
	 * @Override public List<BaseDto<T>> findBaseAll() { repo().fi return
	 * super.SetListBaseImg("avatar", "avatarView", super.findAll()); }
	 */

	@Override
	public List<T> findAll() {
		return super.SetImg("avatar", "avatarView", super.findAll());
	}

	@Override
	public T find(Integer id) {
		// TODO Auto-generated method stub
		return super.SetImgSingle("avatar", "avatarView", super.find(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T insert(T obj) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		pessoa = (P) obj;
		pessoa.setEmpresa(user.getEmpresa());
		getRepoContact().save(pessoa.getContatoPrincipal());
		getRepoEnd().save(pessoa.getEnderecoPrincipal());
		pessoa.setId(null);
		pessoa.setEnderecos((List<BE>) new ArrayList<EnderecoEmpresas>());
		pessoa = getRepo().save(pessoa);
		pessoa.getEnderecoPrincipal().setPessoas(obj);
		getRepoEnd().save(pessoa.getEnderecoPrincipal());

		pessoa.getContatoPrincipal().setPessoas(obj);
		getRepoContact().save(pessoa.getContatoPrincipal());
		return (obj);
		// TODO Auto-generated method stub
		// return super.insert(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T update(T obj) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		pessoa = (P) obj;
		if (pessoa.getContatoPrincipal() != null)
			getRepoContact().save(pessoa.getContatoPrincipal());

		if (pessoa.getEnderecoPrincipal() != null)
			getRepoEnd().save(pessoa.getEnderecoPrincipal());

		obj.setEmpresa(user.getEmpresa());
		obj = (T) getRepo().save(pessoa);
		return (obj);
	}
	@Override
	public List<BaseDto> findBaseAll() {
		List<BaseDto> baseDto = new ArrayList<>();
		for (T empresas :  findAll()) {
			pessoa = (P) empresas;
			baseDto.add(new BaseDto(pessoa.getId(), pessoa.getName(), pessoa.getCpfOnCnpj(), pessoa.getAvatar(),
					pessoa.getAvatarView(), pessoa.getEmail()));
		}
		return baseDto;
	}
	@Override
	public String uploadProfilePicture(MultipartFile file, Integer id, String fieldname) {
		Field fldfonte = null;
		Object value = null;

		String filename = "";
		obj = repo().findById(id).get();
		try {

			fldfonte = getClasse().getDeclaredField(fieldname);

			fldfonte.setAccessible(true);
			value = fldfonte.get(obj);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update(obj);

		try {
			return filesService.downloadFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldname;
	}
}
