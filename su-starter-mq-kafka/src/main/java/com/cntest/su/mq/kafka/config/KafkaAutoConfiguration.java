package com.cntest.su.mq.kafka.config;

import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
@ConditionalOnClass(EnableKafka.class)
public class KafkaAutoConfiguration {


  @Bean(name = "kafkaListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
      ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
      ConsumerFactory<Object, Object> kafkaConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory, kafkaConsumerFactory);
    // 扩展 配置
    // 扩展消费者配置
    Map<String, Object> consumerConfigurationProperties =
        kafkaConsumerFactory.getConfigurationProperties();
    if (consumerConfigurationProperties.containsKey("ack-on-error")) {
      boolean ackOnError = true;
      String ackOnErrorStr = consumerConfigurationProperties.get("ack-on-error").toString();
      ackOnError = Boolean.valueOf(ackOnErrorStr);
      factory.getContainerProperties().setAckOnError(ackOnError);
    }

    return factory;
  }
}
