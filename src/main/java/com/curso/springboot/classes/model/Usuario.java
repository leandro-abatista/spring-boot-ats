package com.curso.springboot.classes.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String login;

	private String senha;
	
	@OneToMany(fetch = FetchType.EAGER)// sempre carregar
	@JoinTable(name = "usuarios_role",
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role_acesso"))//cria a tabela de acesso do usuário
	private List<RoleAcesso> roleAcessos;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roleAcessos;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	
}
