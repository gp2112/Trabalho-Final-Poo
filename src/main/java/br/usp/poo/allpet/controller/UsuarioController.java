package br.usp.poo.allpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.usp.poo.allpet.model.Usuario;
import br.usp.poo.allpet.repository.UsuarioRepository;
import br.usp.poo.allpet.security.AuthUser;
import br.usp.poo.allpet.service.UsuarioService;
import br.usp.poo.allpet.viewmodel.UsuarioViewModel;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    //Página de cadastro do usuário
    @GetMapping("/cadastro")
    public ModelAndView getCadastro() {
    	//verificando se o usuário já está logado
        String email = AuthUser.getEmail();
        if (email != null)
            return new ModelAndView("redirect:/"); //Usuário logado, redireciona para a página incial
        return new ModelAndView("usuario/cadastro");
    }
    
    //Adicionando um novo usuário no Banco de Dados
    @PostMapping("/cadastro")
    public ModelAndView addUser(@RequestParam String email, @RequestParam String nome, @RequestParam String senha, @RequestParam String cpf,
                          @RequestParam String cidade, @RequestParam String telefone) {

         Boolean r = usuarioService.addUser(
                nome, email,
                senha, cpf,
                cidade, telefone
        );
         if (r)
             return new ModelAndView("redirect:/login?sucesso=true&retorno=Usuario+cadastrado+com+sucesso!");
         //caso o usuario já exista não é possível cadastrar, logo retorna sucesso=false na url
        return new ModelAndView("redirect:/login?sucesso=false&retorno=Usuario+ja+existe!");
    }

    //Página de login
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required=false) Boolean sucesso, @RequestParam(required = false) String retorno) {
    	//verifica se o usário já está logado
        String email = AuthUser.getEmail();
        if (email != null)
            return new ModelAndView("redirect:/"); //Usuário logado, redireciona para a página incial
        
        //caso o usuário não esteja logado
        UsuarioViewModel uvm = usuarioService.loginPage(sucesso, retorno);
        //avisa o front end se houve um erro ou logout
        return new ModelAndView("usuario/login", uvm.getParams());
    }
    
    
    //Página de perfil
    @GetMapping("/perfil")
    public ModelAndView perfil() {
    	//Encontra o usúario pelo email e mostra todos os seus anúncios
        String email = AuthUser.getEmail();
        Usuario user = usuarioRepository.findByEmail(email);
    	UsuarioViewModel uvm = usuarioService.getInfosUsuario(user);
    	
    	return new ModelAndView("usuario/perfil", uvm.getParams());
    }

    @PostMapping("/perfil")
    public ModelAndView alterPerfil(@RequestParam String email, @RequestParam String nome, @RequestParam String senha, @RequestParam String cpf,
                                    @RequestParam String cidade, @RequestParam String telefone) {
        //Encontra o usúario pelo email e mostra todos os seus anúncios
        String authEmail = AuthUser.getEmail();
        Usuario authUser = usuarioRepository.findByEmail(authEmail);

        Usuario usuario = new Usuario(authUser.getId(), nome, cidade, cpf, telefone, email, new BCryptPasswordEncoder().encode(senha));
        return usuarioService.updateUser(usuario);
    }

}
