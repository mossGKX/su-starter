package com.cntest.su.process.activiti.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.cntest.su.process.activiti.event.listener.ActivitiExecutionListener;
import com.cntest.su.process.activiti.event.listener.ActivitiTaskListener;
import com.cntest.su.process.activiti.service.impl.ProcessDefinitionServiceImpl;
import com.cntest.su.process.activiti.service.impl.ProcessRuntimeServiceImpl;
import com.cntest.su.process.event.EventManager;

@Configuration
@EnableConfigurationProperties(ActivitiProperties.class)
public class ActivitiAutoConfiguration extends AbstractProcessEngineAutoConfiguration {

  @Autowired
  private ActivitiProperties activitiProperties;


  // @Bean(name = "suActivitiDataSource")
  @Bean
  @ConditionalOnMissingBean
  public DataSource activitiDataSource() {
    return this.actDataSource();
  }

  // @Bean(name = "suActivitiTransactionManager")
  @Bean
  @ConditionalOnMissingBean
  public PlatformTransactionManager txManager(DataSource dataSource) {
    return this.actTxManager(dataSource);
  }

  private PlatformTransactionManager actTxManager(DataSource dataSource) {
    DataSourceTransactionManager dataSourceTransactionManager =
        new DataSourceTransactionManager(dataSource);
    return dataSourceTransactionManager;
  }

  private DataSource actDataSource() {
    DruidDataSource datasource = new DruidDataSource();
    datasource.setUrl(this.activitiProperties.getUrl());
    datasource.setUsername(this.activitiProperties.getUsername());
    datasource.setPassword(this.activitiProperties.getPassword());
    datasource.setDriverClassName(this.activitiProperties.getDriverClassName());
    datasource.setInitialSize(this.activitiProperties.getInitialSize());
    datasource.setMinIdle(this.activitiProperties.getMinIdle());
    datasource.setMaxActive(this.activitiProperties.getMaxActive());
    datasource.setMaxWait(this.activitiProperties.getMaxWait());
    datasource.setTimeBetweenEvictionRunsMillis(
        this.activitiProperties.getTimeBetweenEvictionRunsMillis());
    datasource
        .setMinEvictableIdleTimeMillis(this.activitiProperties.getMinEvictableIdleTimeMillis());
    datasource.setValidationQuery(this.activitiProperties.getValidationQuery());
    datasource.setTestWhileIdle(this.activitiProperties.getTestWhileIdle());
    datasource.setTestOnBorrow(this.activitiProperties.getTestOnBorrow());
    datasource.setTestOnReturn(this.activitiProperties.getTestOnReturn());
    datasource.setRemoveAbandoned(this.activitiProperties.getRemoveAbandoned());
    datasource.setRemoveAbandonedTimeout(this.activitiProperties.getRemoveAbandonedTimeout());
    datasource.setLogAbandoned(this.activitiProperties.getLogAbandoned());
    datasource.setDefaultAutoCommit(this.activitiProperties.getDefaultAutoCommit());

    return datasource;
  }



  // 注入数据源和事务管理器
  @Bean
  public SpringProcessEngineConfiguration springProcessEngineConfiguration(
      SpringAsyncExecutor springAsyncExecutor) throws IOException {

    DataSource dataSource = this.actDataSource();
    PlatformTransactionManager transactionManager = this.actTxManager(dataSource);
    SpringProcessEngineConfiguration processEngineConfiguration = this
        .baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
    processEngineConfiguration.setActivityFontName("宋体");
    processEngineConfiguration.setLabelFontName("宋体");
    processEngineConfiguration.setAnnotationFontName("宋体");
    return processEngineConfiguration;

  }


  /**
   * 注入业务处理对象
   */


  @Bean
  public ProcessDefinitionServiceImpl getProcessDefinitionService() {
    ProcessDefinitionServiceImpl processDefinitionService = new ProcessDefinitionServiceImpl();
    return processDefinitionService;
  }

  @Bean
  public ProcessRuntimeServiceImpl getProcessRuntimeService() {
    ProcessRuntimeServiceImpl processRuntimeService = new ProcessRuntimeServiceImpl();
    return processRuntimeService;
  }

  /*
   * @Bean public ServiceProcessorManager getServiceProcessorManager() { ServiceProcessorManager
   * serviceProcessorManager = new ServiceProcessorManager(); return serviceProcessorManager; }
   * 
   * @Bean public EventManager getEventManager() { EventManager eventManager = new EventManager();
   * return eventManager; }
   */

  @Bean(name = "activitiTaskListener")
  public ActivitiTaskListener getActivitiTaskListene(EventManager eventManager) {
    ActivitiTaskListener activitiTaskListener = new ActivitiTaskListener(eventManager);
    return activitiTaskListener;
  }

  @Bean(name = "activitiExecutionListener")
  public ActivitiExecutionListener getActivitiExecutionListener(EventManager eventManager) {
    ActivitiExecutionListener activitiExecutionListener =
        new ActivitiExecutionListener(eventManager);
    return activitiExecutionListener;
  }

}
