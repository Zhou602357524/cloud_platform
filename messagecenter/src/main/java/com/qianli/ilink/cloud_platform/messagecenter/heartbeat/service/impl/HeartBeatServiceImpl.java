package com.qianli.ilink.cloud_platform.messagecenter.heartbeat.service.impl;

import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.bean.UdpServerNode;
import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.config.HeartBeatConfig;
import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.service.HeartBeatSender;
import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.service.UdpServerNodeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


@Slf4j
@Service
public class HeartBeatServiceImpl implements HeartBeatSender {

    @Autowired
    private HeartBeatConfig heartBeatConfig;

    @Autowired
    private UdpServerNodeManager udpServerNodeManager;

    @Override
    public void execute() {
        String udpServerListStr = heartBeatConfig.getUdpServerList();
        if(StringUtils.isEmpty(udpServerListStr)){
            log.error("heartBeat udpServerList 配置项异常...");
            return;
        }
        Integer retryTimes = heartBeatConfig.getRetryTimes();
        if(retryTimes == null){
            log.error("heartBeat retryTimes 配置项异常...");
            return;
        }
        Arrays.asList(udpServerListStr.split(",")).parallelStream().forEach(udpServer ->{
            String ip = udpServer.split(":")[0];
            Integer port = Integer.parseInt(udpServer.split(":")[1]);
            boolean isSuccess = this.sendHeartBeat(ip,port);
            if(isSuccess){
                //如果成功，强制刷新node.
                udpServerNodeManager.refresh(udpServer,UdpServerNode.builder().ip(ip).port(port).retryTimes(0).status(0).build());
            }else{
                //如果失败，先判断是否含有node
               UdpServerNode node = udpServerNodeManager.get(udpServer);
               //如果包含node,则对失败次数加1 ps:失败3次以内认为有效
               if(node == null){
                   //因为有node初始化,这段逻辑可能不会被执行
                   udpServerNodeManager.refresh(udpServer,UdpServerNode.builder().ip(ip).port(port).retryTimes(1).status(0).build());
               }else{
                   if(node.getStatus() == 1){
                       log.error("node {}:{} is invalid...",ip,port);
                       return;
                   }
                   int currentRetryTimes = node.getRetryTimes()+1;
                   if(currentRetryTimes > retryTimes){
                       node.setStatus(1);
                       log.error("node {}:{} is invalid...",ip,port);
                   }else{
                       node.setRetryTimes(retryTimes);
                       log.warn("node {}:{} retry {} times...",ip,port,retryTimes);
                   }
                   udpServerNodeManager.refresh(udpServer,node);
               }
            }
        });
    }

    private boolean sendHeartBeat(String ip, Integer port) {
        boolean flag;
        Socket client = null;
        try {
            client = new Socket();
            InetSocketAddress address = new InetSocketAddress(ip,port);
            client.connect(address, 1000);
            flag = true;
        } catch (IOException e) {
            log.error("heartBeat send fail...",e);
            flag = false;
        }finally{
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    log.error("socket client close fail...",e);
                }
            }
        }
        return flag;
    }

}
