package com.qianli.ilink.cloud_platform.messagecenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.qianli.ilink.cloud_platform.messagecenter.core.kafka.producer.KafkaMessageSender;
import com.qianli.ilink.cloud_platform.messagecenter.pojo.dto.Message;
import com.qianli.ilink.cloud_platform.messagecenter.service.MessageSender;
import com.qianli.ilink.cloud_platform.messagecenter.utils.IdGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MessageSenderImpl implements MessageSender {

    @Autowired
    private KafkaMessageSender kafkaMessageSender;

    @Value("${kafka.messageTopic}")
    private String messageTopic;

    @Override
    public void send(Message message) {
        kafkaMessageSender.execute(messageTopic,IdGenerater.kafkaKey(), JSON.toJSONString(message));
    }
}
