package br.usp.poo.allpet.controller;

import br.usp.poo.allpet.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.usp.poo.allpet.service.IndexService;
import br.usp.poo.allpet.viewmodel.IndexViewModel;

@RestController
public class IndexController {
	
	@Autowired
	private IndexService indexService;

	@GetMapping
	public ModelAndView getIndex() {
		IndexViewModel vm = indexService.getIndexViewModel();
		return new ModelAndView("index/inicio", vm.getParams());
	}

	@GetMapping("/cadastro")
	public ModelAndView getCadastro() {
		return new ModelAndView("index/cadastro");
	}
/*
	@PostMapping("/addUser")
	public ModelAndView addUser(@RequestBody Usuario user) {

	}*/
}
