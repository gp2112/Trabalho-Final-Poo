package br.usp.poo.allpet.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.usp.poo.allpet.enums.Animal;
import br.usp.poo.allpet.model.Anuncio;
import br.usp.poo.allpet.model.Usuario;
import br.usp.poo.allpet.repository.AnuncioRepository;
import br.usp.poo.allpet.repository.UsuarioRepository;
import br.usp.poo.allpet.security.AuthUser;
import br.usp.poo.allpet.viewmodel.AnuncioViewModel;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    //Página de criação de anúncio
    public ModelAndView getCriarPage() {
        String email = AuthUser.getEmail();
        Usuario usuario = usuarioRepository.findByEmail(email);
        AnuncioViewModel avm = new AnuncioViewModel(usuario);
        //Passa o úsuario para o ModelAndView também
        return new ModelAndView("anuncio/criar", avm.getParams());
    }

    //Página de anúncio singular
    public AnuncioViewModel getAnuncio(int id) {
    	if (anuncioRepository.existsById(id))
    		return new AnuncioViewModel(anuncioRepository.getById(id));
    	return new AnuncioViewModel();
    }
    
    //Cadastrar um anúncio
    public boolean cadastrar(String titulo, String user_email, String cidade,
                          String endereco, String descricao, String telefone,
                          Animal animal, String email, MultipartFile foto) {
    	
    	if(titulo.length() == 0 || user_email.length() == 0 || cidade.length() == 0 || endereco.length() == 0
    			|| descricao.length() == 0 || telefone.length() == 0 || email.length() == 0 || foto == null) {
    		return false;
    	}
    	
        Usuario usuario = usuarioRepository.findByEmail(user_email);
        
        byte[] bytes=null;
		try {
			bytes = foto.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Pega os bytes do arquivo da foto passado
		
		
        Anuncio newAnuncio = new Anuncio(null, titulo, descricao, endereco, cidade, telefone, email, animal, usuario.getId(), bytes);
        anuncioRepository.save(newAnuncio);
		return true;
    }
    
    //Busca os anúncios dado uma cidade e um animal
    public AnuncioViewModel buscar(String cidade, Animal animal) {
        if (animal == null && (cidade == null || cidade.length() == 0))
            return new AnuncioViewModel(anuncioRepository.findAll());
    	
        if (cidade != null && cidade.length() > 0 && animal != null)
            return new AnuncioViewModel(anuncioRepository.getByCidadeAnimal(cidade, animal));
        
        if (animal == null)
            return new AnuncioViewModel(anuncioRepository.getByCidade(cidade));

        return new AnuncioViewModel(anuncioRepository.getByAnimal(animal));
        
    }
    
    //Deleta um anúncio pelo id
    public String deleteAnuncio (String idAnuncio) {
    	Integer id = Integer.parseInt(idAnuncio);
    	
    	Anuncio anuncio = anuncioRepository.getById(id);
    	Usuario user = usuarioRepository.getById( anuncio.getUsuario() );
    	
    	//verifica se o anúncio pertence ao usuário logado
    	if ( (user.getEmail()).equals( AuthUser.getEmail() ) ) {
        	anuncioRepository.deleteById(id);
        	return "done";
    	}

    	return "error";
    }

}
