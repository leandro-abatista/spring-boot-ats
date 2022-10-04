package com.curso.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.curso.springboot.classes.model.Pessoa;
import com.curso.springboot.repositories.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	/**
	 * Método de início
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		view.addObject("pessoas", pessoaRepository.findAll());
		view.addObject("objpessoa", new Pessoa());
		return view;
	}
	
	/**
	 * Método para salvar uma pessoa
	 * Os dois asteríscos ignora qualquer coisa antes
	 * @param pessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		
		pessoaRepository.save(pessoa);
		
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoas = pessoaRepository.findAll();
		view.addObject("pessoas", pessoas);
		view.addObject("objpessoa", new Pessoa());
		return view;
	}
	
	/**
	 * Método para listar as pessoas em tela
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView pessoas() {
		
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoas = pessoaRepository.findAll();
		view.addObject("pessoas", pessoas);
		view.addObject("objpessoa", new Pessoa());
		return view;
	}
	
	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		view.addObject("objpessoa", pessoa.get());
		return view;
	}
	
	@GetMapping("/removerpessoa/{idpessoa}")
	public ModelAndView remover(@PathVariable("idpessoa") Long idpessoa) {
		
		pessoaRepository.deleteById(idpessoa);
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		view.addObject("pessoas", pessoaRepository.findAll());
		view.addObject("objpessoa", new Pessoa());
		return view;
	}
	
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		view.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		view.addObject("objpessoa", new Pessoa());
		return view;
	}
}
