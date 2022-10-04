package com.curso.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.classes.model.Pessoa;

@Repository/*Esta anotação é opcional*/
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

	/*Método que retorna uma lista de pessoas filtradas*/
	@Query("select p from Pessoa p where p.nome like %?1%")/*JPQL*/
	List<Pessoa> findPessoaByName(String nome);
}
