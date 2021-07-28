package br.usp.poo.allpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.usp.poo.allpet.enums.Animal;
import br.usp.poo.allpet.security.AuthUser;
import br.usp.poo.allpet.service.AnuncioService;
import br.usp.poo.allpet.viewmodel.AnuncioViewModel;

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
        return anuncioService.getCriarPage();
    }
    
    //Recebe os dados do anúncio e adiciona no banco de dados
    @PostMapping("/criar")
    public ModelAndView criar(@RequestParam String titulo,
                          @RequestParam String cidade,
                          @RequestParam String endereco,
                          @RequestParam String descricao,
                          @RequestParam String telefone,
                          @RequestParam String email,
                          @RequestParam Animal animal,
                          @RequestParam MultipartFile foto) {
        String user_mail = AuthUser.getEmail();
        
        boolean resultado = anuncioService.cadastrar(titulo, user_mail, cidade, endereco, descricao, telefone, animal, email, foto);
        
        //Se deu algum erro ao criar o anuncio o usuário é redirecionado para a página de cadastro de anúncios novamente
        if(resultado == false)
        	return new ModelAndView("redirect:/anuncio/criar?sucesso=false&retorno=Campo+nulo");
        
		return new ModelAndView("redirect:/?sucesso=true&retorno=anuncio+cadastrado");


    }
    
    //Procura os anúncios que estão de acordo com uma região e animal
    @GetMapping("/busca")
    public ModelAndView busca(@RequestParam(required=false) String cidade, @RequestParam(required=false) Animal animal) {

        AnuncioViewModel avm = anuncioService.buscar(cidade, animal);
        return new ModelAndView("index/inicio", avm.getParams());
    }
    
    //Deleta um anúncio, dado o id, do banco de dados
    @PostMapping("/delete")
    public ModelAndView excluirAnuncio(@RequestParam String idAnuncio){
    	String str = anuncioService.deleteAnuncio(idAnuncio);
    	
    	//após o delete redireciona para a página do perfil do usuário logado no site
    	//adiona na url o retorno da busca, done ou error
    	return new ModelAndView("redirect:/perfil?" + str);
    }
    
    //Trata a exceção de tamanho maximo atingido do arquivo da foto, que ocorre no criar (post)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
      MaxUploadSizeExceededException exc) {
    	
    	//redireciona para a página de criação com
    	//adiciona na url o retorno que a operação foi mal-sucedida e o arquivo é muito grande
        return new ModelAndView("redirect:/anuncio/criar?sucesso=false&retorno=arquivo+grande+de+mais");
    }

}
