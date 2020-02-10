package com.cntest.su.process.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.process.event.EventManager;
import com.cntest.su.process.processor.ServiceProcessorManager;

@Configuration
public class ProcessAutoConfiguration {

  @Bean
  public ServiceProcessorManager getServiceProcessorManager() {
    ServiceProcessorManager serviceProcessorManager = new ServiceProcessorManager();
    return serviceProcessorManager;
  }

  @Bean
  public EventManager getEventManager() {
    EventManager eventManager = new EventManager();
    return eventManager;
  }

}
