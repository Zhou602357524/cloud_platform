package com.qianli.ilink.cloud_platform.messagecenterudp.service.impl;

import com.alibaba.fastjson.JSON;
import com.qianli.ilink.cloud_platform.messagecenterudp.kafka.KafkaMessageSender;
import com.qianli.ilink.cloud_platform.messagecenterudp.kafka.KafkaProducerConfig;
import com.qianli.ilink.cloud_platform.messagecenterudp.pojo.dto.Message;
import com.qianli.ilink.cloud_platform.messagecenterudp.service.MessageSender;
import com.qianli.ilink.cloud_platform.messagecenterudp.utils.IdGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MessageSenderImpl implements MessageSender {

    @Autowired
    private KafkaMessageSender kafkaMessageSender;

    @Autowired
    private KafkaProducerConfig kafkaConfig;

    @Async(value = "messageExecutor")
    @Override
    public void send(Message message) {
        kafkaMessageSender.execute(kafkaConfig.getMessageTopic(),IdGenerater.kafkaKey(), JSON.toJSONString(message));
    }
}
