package com.curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.curso.springboot.service.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetailsService userDetailsService;

	@Override /* Configura as solicitações de acesso por http*/
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf()
		.disable()// desabilita as configurações padrão de memória do spring
		.authorizeRequests()// permitir restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() //qualquer usuário acessa a página
		.antMatchers(HttpMethod.GET, "cadastropessoa").hasAnyRole("ROLE_ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin().permitAll()//permiti qualquer usuário
		.and()
		.logout() //mapeia a url de sair do sistema e invalida o usuário autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	@Override/* Cria autênticação do usuário com banco de dados  ou memória*/
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
		/*
		auth
		.inMemoryAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())
		.withUser("admin")
		.password("$2a$10$9V2gOOWzyGRhpcd/gwFNne..IXqNtc2d/t0tZcwqIbLqE/wrKlAcq")
		.roles("ADMIN");
		*/
	}
	
	@Override/* Ignora url's específicas*/
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring()
		.antMatchers("/materialize/**");
	}
}
