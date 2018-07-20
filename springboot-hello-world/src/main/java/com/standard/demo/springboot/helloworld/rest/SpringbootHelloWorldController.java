package com.standard.demo.springboot.helloworld.rest;

import com.standard.demo.springboot.helloworld.domain.Actor;
import com.standard.demo.springboot.helloworld.domain.Greeting;
import com.standard.demo.springboot.helloworld.service.ActorService;
import com.standard.sfg.audit.ExecutionContextHolder;
import com.standard.sfg.audit.springboot.SfgAuditableFactory.SfgAuditor;
import com.standard.sfg.security.springboot.SfgJwtFactory.SfgJwt;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringbootHelloWorldController {

  private static final Logger logger
      = LoggerFactory.getLogger(SpringbootHelloWorldController.class);

  @Autowired
  private SfgJwt sfgJwt;
  @Autowired
  private SfgAuditor sfgAuditor;
  @Autowired
  private ActorService actorService;

  /**
   * Provides a list of actors.
   *
   * @param sfgGtid String containing GTID
   * @return Iterable of Actor
   */
  @RequestMapping(value = "/listActors", method = RequestMethod.GET)
  public Iterable<Actor> listActors(@RequestHeader(value = "x-sfg-gtid") String sfgGtid) {

    /*
     * Initializing the return value with empty list here to satisfy the compiler,
     * but the actual processing should occur in the try block.
     * Avoid
     *        return actorService.getActors();
     * because
     * ExecutionContextHolder.clear() will have to occur before the return statement, and
     * the ExecutionContext will not be available to the ActorService processing its getActors().
     */
    Iterable<Actor> listOfActors = () -> new ArrayList<Actor>().iterator();

    StopWatch stopWatch = new StopWatch();


    /*
     * All processing should occur inside try/catch/finally block to ensure proper
     * error handling and cleanup.
     */
    try {
      stopWatch.start();

      /*
       * -------------------------------------------------------------------------------------------
       * audit info setup
       * -------------------------------------------------------------------------------------------
       */
      final String operationName = "listActors";
      String serviceName = getClass().getPackage().getImplementationTitle();
      String serviceVersion = getClass().getPackage().getImplementationVersion();
      ExecutionContextHolder.set(sfgAuditor.createAndValidateExecutionContext(
          sfgGtid,
          operationName,
          sfgJwt,
          serviceName,
          serviceVersion));

      /*
       * -------------------------------------------------------------------------------------------
       * actual business logic execution
       * -------------------------------------------------------------------------------------------
       */
      if (logger.isWarnEnabled()) {
        logger.warn("{},  yep ... still working on it! *** ",
            sfgAuditor.auditableLogMessage(ExecutionContextHolder.get()));
      }

      listOfActors = actorService.listAllActors();

    } catch (Throwable throwable) {
      logger.error("{} Encountered unexpected exception",
          sfgAuditor.auditableLogMessage(ExecutionContextHolder.get()),
          throwable.getMessage()
      );
    } finally {

      // stop the stopwatch after all business logic processing is complete.
      stopWatch.stop();

      if (logger.isInfoEnabled()) {
        logger.info("{}, duration={}ms",
            sfgAuditor.auditableLogMessage(ExecutionContextHolder.get()),
            stopWatch.getTotalTimeMillis());
      }

      /*
       * -------------------------------------------------------------------------------------------
       * !!!! absolutely MANDATORY cleanup !!!!
       * clear ExecutionContextHolder before completing a RequestMapping method.
       * -------------------------------------------------------------------------------------------
       */
      ExecutionContextHolder.clear();
    }

    return listOfActors;
  }


  /**
   * Says "hello" in lowercase.
   *
   * @param sfgGtid String containing GTID
   * @return Greeting
   */
  @RequestMapping("/hello")
  public Greeting sayHello(@RequestHeader(value = "x-sfg-gtid") String sfgGtid) {

    /*
     * ---------------------------------------------------------------------------------------------
     * audit info setup
     * ---------------------------------------------------------------------------------------------
     */
    final String operationName = "sayHello";
    String serviceName = getClass().getPackage().getImplementationTitle();
    String serviceVersion = getClass().getPackage().getImplementationVersion();
    ExecutionContextHolder.set(sfgAuditor.createAndValidateExecutionContext(
        sfgGtid,
        operationName,
        sfgJwt,
        serviceName,
        serviceVersion));
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    /*
     * ---------------------------------------------------------------------------------------------
     * actual business logic execution
     * ---------------------------------------------------------------------------------------------
     */

    // stop the stopwatch after all business logic processing is complete.
    stopWatch.stop();

    if (logger.isInfoEnabled()) {
      logger.info("{}, duration={}ms",
          sfgAuditor.auditableLogMessage(ExecutionContextHolder.get()),
          stopWatch.getTotalTimeMillis());
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * !!!! absolutely MANDATORY cleanup !!!!
     * clear ExecutionContextHolder before completing a RequestMapping method.
     * ---------------------------------------------------------------------------------------------
     */

    ExecutionContextHolder.clear();


    /*
     * ---------------------------------------------------------------------------------------------
     * return business logic outcome if applicable
     * ---------------------------------------------------------------------------------------------
     */
    return new Greeting("Hello there!");
  }


  /**
   * Says "hello" in caps.
   *
   * @param sfgGtid String GTID
   * @return Greeting
   */
  @RequestMapping("/helloUpper")
  public Greeting sayHelloUpper(@RequestHeader(value = "x-sfg-gtid") String sfgGtid) {

    /*
     * ---------------------------------------------------------------------------------------------
     * audit info setup
     * ---------------------------------------------------------------------------------------------
     */
    final String operationName = "helloUpper";
    String serviceName = getClass().getPackage().getImplementationTitle();
    String serviceVersion = getClass().getPackage().getImplementationVersion();
    ExecutionContextHolder.set(sfgAuditor.createAndValidateExecutionContext(
        sfgGtid,
        operationName,
        sfgJwt,
        serviceName,
        serviceVersion));
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    /*
     * ---------------------------------------------------------------------------------------------
     * actual business logic execution
     * ---------------------------------------------------------------------------------------------
     */
    if (logger.isWarnEnabled()) {
      logger.warn("{},  This is going to take a while ... *** ",
          sfgAuditor.auditableLogMessage(ExecutionContextHolder.get()));
    }

    // stop the stopwatch after all business logic processing is complete.
    stopWatch.stop();

    if (logger.isInfoEnabled()) {
      logger.info("{}, duration={}ms",
          sfgAuditor.auditableLogMessage(ExecutionContextHolder.get()),
          stopWatch.getTotalTimeMillis());
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * !!!! absolutely MANDATORY cleanup !!!!
     * clear ExecutionContextHolder before completing a RequestMapping method.
     * ---------------------------------------------------------------------------------------------
     */
    ExecutionContextHolder.clear();

    return new Greeting("HELLO THERE!");
  }


}
