package com.curso.springboot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.classes.model.Pessoa;

@Repository/*Esta anotação é opcional*/
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

}
