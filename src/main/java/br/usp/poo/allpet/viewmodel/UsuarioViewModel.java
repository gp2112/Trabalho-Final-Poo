package br.usp.poo.allpet.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.usp.poo.allpet.model.Anuncio;
import br.usp.poo.allpet.model.Usuario;
import br.usp.poo.allpet.security.AuthUser;

public class UsuarioViewModel extends ViewModel{

	private Map<String, Object> params = new HashMap<>();

    //Adiciona parâmetros de retorno
	public UsuarioViewModel(Boolean success, String retorno) {
		if (success!=null) {
			params.put("sucesso", success);
			params.put("retorno", retorno);
		}
	}

    //Adiciona um usuário
	public UsuarioViewModel(Usuario usuario) {
		params.put("usuario", usuario);
	}

    //Adiciona um usuário e seus anúncios
	public UsuarioViewModel(Usuario usuario, List<Anuncio> anuncios) {
		params.put("usuario", usuario);
		params.put("anuncios", anuncios);
	}
	
    //adiciona o email na hashMap e retorna todos os parâmetros
	@Override
	public Map<String, Object> getParams() {
		String authEmail = AuthUser.getEmail();
		params.put("username", authEmail);
		return params;
	}

}
