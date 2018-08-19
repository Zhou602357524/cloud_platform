package com.qianli.ilink.cloud_platform.messagecenter.heartbeat.service.impl;

import com.alibaba.fastjson.JSON;
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
import java.util.Arrays;


@Slf4j
@Service
public class HeartBeatServiceImpl implements HeartBeatSender {

    @Autowired
    private HeartBeatConfig heartBeatConfig;

    @Autowired
    private UdpServerNodeManager udpServerNodeManager;

    @Override
    public void execute() {
        log.debug("heartBeat schedule task start...");
        String udpServerListStr = heartBeatConfig.getUdpServerList();
        if(StringUtils.isEmpty(udpServerListStr)){
            log.error("heartBeat udpServerList 配置项异常...");
            return;
        }
        Arrays.asList(udpServerListStr.split(",")).parallelStream().forEach(udpServer ->{
            boolean isSuccess = this.sendHeartBeat(udpServer);
            if(isSuccess){
                udpServerNodeManager.refreshWithSuccess(udpServer);
            }else{
                udpServerNodeManager.refreshWithFail(udpServer);
            }
        });
        log.info("current udp server nodes : {}", JSON.toJSONString(udpServerNodeManager.getValidUdpServerNodes()));
        log.debug("heartBeat schedule task end...");
    }

    private boolean sendHeartBeat(String udpServer) {
        boolean flag;
        Socket client = null;
        try {
            String ip = udpServer.split(":")[0];
            Integer port = Integer.parseInt(udpServer.split(":")[1]);
            client = new Socket();
            InetSocketAddress address = new InetSocketAddress(ip,port);
            client.connect(address, 1000);
            flag = true;
        } catch (IOException e) {
            log.error("heartBeat send fail...{}",e.getLocalizedMessage());
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
