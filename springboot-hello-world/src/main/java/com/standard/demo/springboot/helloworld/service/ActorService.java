package com.standard.demo.springboot.helloworld.service;

import com.standard.demo.springboot.helloworld.domain.Actor;

public interface ActorService {

  Iterable<Actor> listAllActors();

  Actor findById(Integer id);

  Actor saveActor(Actor actor);

}
