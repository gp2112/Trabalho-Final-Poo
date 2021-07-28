package br.usp.poo.allpet.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.usp.poo.allpet.enums.Animal;
import br.usp.poo.allpet.model.Anuncio;
import br.usp.poo.allpet.model.Usuario;
import br.usp.poo.allpet.security.AuthUser;

public class AnuncioViewModel {

    private Map<String, Object> params = new HashMap<>();

    public AnuncioViewModel() {}
    
    //Adiciona um anúncio
    public AnuncioViewModel(Anuncio anuncio) {
        params.put("anuncio", anuncio);
    }

    //Adiciona uma lista de anúncios e a lista de string do enum animais
    public AnuncioViewModel(List<Anuncio> anuncios) {
        params.put("anuncios", anuncios);
        params.put("animais", Animal.getAnimaisString());
    }

    //Adiciona um usuário e a lista de string do enum animais
    public AnuncioViewModel(Usuario usuario) {
    	List<String> strings_animais = Animal.getAnimaisString();
		params.put("animais", strings_animais);
        params.put("usuario", usuario);
	}

	//adiciona o email na hashMap e retorna todos os parâmetros
    public Map<String, Object> getParams() {
        String authEmail = AuthUser.getEmail();
        params.put("username", authEmail);
        return params;
    }
}
