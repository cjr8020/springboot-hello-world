package com.standard.demo.springboot.helloworld.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.standard.demo.springboot.helloworld.domain.Actor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorRepositoryTest {

  private ActorRepository actorRepository;

  @Autowired
  public void setProductRepository(ActorRepository actorRepository) {
      this.actorRepository = actorRepository;
  }
  
  @Test
  public void testFindUser() {
    Logger.getLogger("springboot-hello-world.ActorRepositoryTest")
        .log(Level.WARNING, "***** Executing ActorRepositoryTest. ");
    
    Actor actor = new Actor();
    actor.setUsername("mmouse");
    actor.setEmail("mickey.mouse@demo.com");
    
    // Save actor, verify has id value after save
    assertNull(actor.getId());    //null before save (not found)
    actor.setId(3);
    actorRepository.save(actor);
    assertNotNull(actor.getId()); //not null after save
    
    //fetch from DB
    Actor fetchedActor = actorRepository.findById(actor.getId());
    
    assertNotNull(fetchedActor);
    
    //should be equal
    assertEquals(actor.getId(), fetchedActor.getId());
    assertEquals(actor.getUsername(), fetchedActor.getUsername());
    assertEquals(actor.getEmail(), fetchedActor.getEmail());
    
    //update email and save
    fetchedActor.setEmail("mini.mouse@demo.com");
    actorRepository.save(fetchedActor);
    
    //get from DB, should be updated
    Actor fetchedUpdatedActor = actorRepository.findById(fetchedActor.getId());
    assertEquals(fetchedActor.getEmail(), fetchedUpdatedActor.getEmail());
    
  //verify count of products in DB
    long actorCount = actorRepository.count();
    assertEquals(actorCount, 3);

    //get all products, list should only have three 
    Iterable<Actor> actors = actorRepository.findAll();

    int count = 0;

    for(Actor ac : actors){
        count++;
    }

    assertEquals(count, 3);
    
  }
}