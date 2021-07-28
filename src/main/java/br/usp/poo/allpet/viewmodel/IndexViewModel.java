package br.usp.poo.allpet.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.usp.poo.allpet.enums.Animal;
import br.usp.poo.allpet.model.Anuncio;
import br.usp.poo.allpet.security.AuthUser;

public class IndexViewModel extends ViewModel {

	private Map<String, Object> params = new HashMap<>();

    //Adiciona uma lista de anúncios	
	public IndexViewModel(List<Anuncio> anuncios) {
		params.put("anuncios", anuncios);
		params.put("animais", Animal.getAnimaisString());
	}
	
    //adiciona o email na hashMap e retorna todos os parâmetros
	@Override
	public Map<String, Object> getParams() {
		String authEmail = AuthUser.getEmail();
		params.put("username", authEmail);
		return params;
	}

}
