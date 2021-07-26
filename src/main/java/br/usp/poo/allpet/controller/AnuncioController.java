package br.usp.poo.allpet.controller;

import br.usp.poo.allpet.enums.Animal;
import br.usp.poo.allpet.model.Anuncio;
import br.usp.poo.allpet.service.AnuncioService;
import br.usp.poo.allpet.viewmodel.AnuncioViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/anuncio")
public class AnuncioController {
	
    @Autowired
    private AnuncioService anuncioService;

    //Criando uma página referente ao anúncio de {id}
    @GetMapping("/{id}")
    public ModelAndView getAnuncio(@PathVariable(value="id") short id) {
        AnuncioViewModel avm = anuncioService.getAnuncio(id);
        //retona um ModealAndView com um anúncio
        return new ModelAndView("anuncio/anuncio", avm.getParams());
    }
    
    //Página para criação de um anúncio
    @GetMapping("/criar")
    public ModelAndView criar() {
    	AnuncioViewModel avm = new AnuncioViewModel(Animal.values());
        return new ModelAndView("anuncio/criar", avm.getParams());
    }
    
    //Recebe os dados do anúncio e adiciona no banco de dados
    @PostMapping("/criar")
    public void criar(@RequestParam String titulo,
                          @RequestParam short user_id,
                          @RequestParam String cidade,
                          @RequestParam String endereco,
                          @RequestParam String descricao,
                          @RequestParam String telefone,
                          @RequestParam byte[] foto,
                          @RequestParam String email,
                          @RequestParam Animal animal) {

        anuncioService.cadastrar(titulo, user_id, cidade, endereco, descricao, telefone, foto, animal, email);

    }
    
    //Procura os anúncios que estão de acordo com uma região e animal
    @GetMapping("/busca")
    public ModelAndView busca(@RequestParam(required=false) String regiao, @RequestParam(required=false) Animal animal) {

        AnuncioViewModel avm = anuncioService.buscar(regiao, animal);
        return new ModelAndView("anuncio/busca", avm.getParams());
    }
    
    //Deleta um anúncio, dado o id, do banco de dados
    @PostMapping("/delete")
    public ModelAndView excluirAnuncio(@RequestParam String idAnuncio){
    	String str = anuncioService.deleteAnuncio(idAnuncio);
    	
    	//após o delete redireciona para a página do perfil do usuário logado no site
    	//adiona na url o retorno da busca, done ou error
    	return new ModelAndView("redirect:/perfil?" + str);
    }

}
