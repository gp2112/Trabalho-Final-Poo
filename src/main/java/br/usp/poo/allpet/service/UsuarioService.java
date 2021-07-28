package br.usp.poo.allpet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import br.usp.poo.allpet.model.Usuario;
import br.usp.poo.allpet.repository.AnuncioRepository;
import br.usp.poo.allpet.repository.UsuarioRepository;
import br.usp.poo.allpet.viewmodel.UsuarioViewModel;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    public boolean checkUserInDb(String email) {
        Usuario user = usuarioRepository.findByEmail(email);
        return user!=null;
    }
    
    //Adiciona um novo usuário no banco de dados
    public Boolean addUser(String nome, String email, String senha, String cpf, String cidade, String telefone) {
        boolean isInDb = checkUserInDb(email);
        //Verifica se o usuário já está no banco de dados
        if (isInDb)
            return false;
        //Adiciona no repositório
        Usuario user = new Usuario(null,  nome, cidade, cpf, telefone, email, new BCryptPasswordEncoder().encode(senha));
        usuarioRepository.save(user);
        return true;
    }

    //Alterar as credenciais do usuário
    public ModelAndView updateUser(Usuario usuario) {
        if (usuario.getCidade().length() == 0 || usuario.getNome().length() == 0 ||
                usuario.getCpf().length() == 0 || usuario.getTelefone().length() == 0 ||
                usuario.getEmail().length() == 0 || usuario.getSenha().length() == 0)
            return new ModelAndView("redirect:/perfil?success=false");
        usuarioRepository.save(usuario);
        return new ModelAndView("redirect:/perfil?success=true");
    }

    public Usuario getUserById(short id) {
        return usuarioRepository.getById((id));
    }

    public UsuarioViewModel loginPage(Boolean success, String retorno) {
        return new UsuarioViewModel(success, retorno);
    }
    
    public UsuarioViewModel getInfosUsuario (Usuario usuario) {

    	return new UsuarioViewModel(usuario, anuncioRepository.getByUsuario(usuario.getId()));
    	
    }

}
