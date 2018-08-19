package com.qianli.ilink.cloud_platform.messagecenterudp.service;


import com.qianli.ilink.cloud_platform.messagecenterudp.pojo.dto.Message;

public interface MessageSender {
    void send(Message message);
}
