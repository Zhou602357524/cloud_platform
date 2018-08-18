package com.qianli.ilink.cloud_platform.messagecenter.core.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;

/**
 * @author yanghao
 */
@Slf4j
@Component
public class KafkaMessageSender {

    @Value("${kafka.boostrapServers}")
    private String boostrapServers;

    @Value("${kafka.batchSize}")
    private long batchSize;

    @Value("${kafka.lingerMs}")
    private long lingerMs;

    private KafkaProducer<String,String> producer;

    @PostConstruct
    public void start(){
        log.info("kafka producer initialize..");
        Properties properties = new Properties();
        properties.put("bootstrap.servers",boostrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks","1");
        properties.put("buffer.memory",33554432);
        //1s or 到达batch.size 发送一次
        properties.put("batch.size",batchSize);
        properties.put("linger.ms",lingerMs);
        producer = new KafkaProducer<>(properties);
        log.info("kafka producer start successful..");
    }

    @PreDestroy
    public void destory(){
        producer.close();
        log.info("kafka producer shutdown successful..");
    }

    public void execute(String topic ,String key,String value){
        try{
            producer.send(new ProducerRecord<>(topic,key,value));
        }catch (Exception e){
            log.warn("kafka send fail...",e);
        }
    }

}
