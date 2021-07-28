package br.usp.poo.allpet.enums;

import java.util.ArrayList;
import java.util.List;

public enum Animal {
	CACHORRO, GATO;
	
    public static List<String> getAnimaisString() {
    	//Método para obter a lista com as strings dos nomes dentro do enum
    	//Importante para o front-end
    	List<String> strings_animais = new ArrayList<>();
    	for(Animal a : Animal.values()) {
    		strings_animais.add(a.name());
    	}
    	return strings_animais;
    }
}
