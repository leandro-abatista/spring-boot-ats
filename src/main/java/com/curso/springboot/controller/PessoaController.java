package com.curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.curso.springboot.classes.model.Pessoa;
import com.curso.springboot.classes.model.Telefone;
import com.curso.springboot.repositories.PessoaRepository;
import com.curso.springboot.repositories.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@GetMapping("/principal")
	public String principal() {
		return "principal";
	} 

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
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult result) {
		
		if (result.hasErrors()) {/*se tiver erros*/
			ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoas = pessoaRepository.findAll();
			view.addObject("pessoas", pessoas);
			view.addObject("objpessoa", pessoa);/*Esse objeto vem da view e fica com os dados em tela*/
			
			List<String> mensagem = new ArrayList<>();
			for (ObjectError objectError : result.getAllErrors()) {
				mensagem.add(objectError.getDefaultMessage());/*Vem das anotações da classe pessoa*/
			}
			
			view.addObject("mensagem", mensagem);/*Envia as mensagem para a tela do usuário*/
			return view;
		}
		
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
	
	/**
	 * Método para editar um registro
	 * @param idpessoa
	 * @return
	 */
	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		view.addObject("objpessoa", pessoa.get());
		return view;
	}
	
	/**
	 * Método para remover um registro
	 * @param idpessoa
	 * @return
	 */
	@GetMapping("/removerpessoa/{idpessoa}")
	public ModelAndView remover(@PathVariable("idpessoa") Long idpessoa) {
		
		pessoaRepository.deleteById(idpessoa);
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		view.addObject("pessoas", pessoaRepository.findAll());
		view.addObject("objpessoa", new Pessoa());
		return view;
	}
	
	/**
	 * Método para pesquisar por nome
	 * @param nomepesquisa
	 * @return
	 */
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastropessoa");
		view.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		view.addObject("objpessoa", new Pessoa());
		return view;
	}
	
	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView view = new ModelAndView("cadastro/cadastrotelefones");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		view.addObject("objpessoa", pessoa.get());
		view.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		return view;
	}
	
	@PostMapping("**/addtelefonepessoa/{pessoaid}")
	public ModelAndView addFonePessoa(@Valid Telefone telefone, 
									  @PathVariable("pessoaid") Long pessoaid,
									  BindingResult result) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		if (result.hasErrors()) {
			ModelAndView view = new ModelAndView("cadastro/cadastrotelefones");
			view.addObject("objpessoa", pessoa);
			view.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
			
			List<String> mensagem = new ArrayList<>();
			for (ObjectError objectError : result.getAllErrors()) {
				mensagem.add(objectError.getDefaultMessage());/*Vem das anotações da classe pessoa*/
			}
			
			view.addObject("mensagem", mensagem);/*Envia as mensagem para a tela do usuário*/
			
			return view;
		}
		
		ModelAndView view = new ModelAndView("cadastro/cadastrotelefones");
		view.addObject("objpessoa", pessoa);
		view.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		return view;
	}
	
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone) {
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		
		telefoneRepository.deleteById(idtelefone);
		ModelAndView view = new ModelAndView("cadastro/cadastrotelefones");
		view.addObject("objpessoa", pessoa);
		view.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		return view;
	}
}
