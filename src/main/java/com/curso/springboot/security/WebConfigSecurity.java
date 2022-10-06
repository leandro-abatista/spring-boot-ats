package com.curso.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Override /* Configura as solicitações de acesso por http*/
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf()
		.disable()// desabilita as configurações padrão de memória do spring
		.authorizeRequests()// permitir restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() //qualquer usuário acessa a página
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
		.inMemoryAuthentication()
		.passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("admin")
		.password("admin")
		.roles("ADMIN");
	}
	
	@Override/* Ignora url's específicas*/
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring()
		.antMatchers("/materialize/**");
	}
}
