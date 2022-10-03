package com.curso.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller/*Intercepta requisições vindas das páginas */
public class IndexController {

	@RequestMapping("/")
	public String index() {
		/*Aqui pode colocar qualquer coisa*/
		return "index";
	}
	
}
