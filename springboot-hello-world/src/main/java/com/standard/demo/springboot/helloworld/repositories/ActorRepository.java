package com.standard.demo.springboot.helloworld.repositories;

import com.standard.demo.springboot.helloworld.domain.Actor;
import org.springframework.data.repository.CrudRepository;

/**
 * Actor repository.
 */
public interface ActorRepository extends CrudRepository<Actor, String> {

  Actor findById(Integer id);
  
}
