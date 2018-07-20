package com.standard.demo.springboot.helloworld.service;

import com.standard.demo.springboot.helloworld.domain.Actor;
import com.standard.demo.springboot.helloworld.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorRepository actorRepository;

  @Autowired
  public void setAcrotRepository(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor findById(Integer id) {
    return actorRepository.findById(id);
  }

  public Actor saveActor(Actor actor) {
    return actorRepository.save(actor);
  }

  public Iterable<Actor> listAllActors() {
    return actorRepository.findAll();
  }

}