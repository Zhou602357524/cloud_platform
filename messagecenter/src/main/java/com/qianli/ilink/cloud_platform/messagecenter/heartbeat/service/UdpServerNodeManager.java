package com.qianli.ilink.cloud_platform.messagecenter.heartbeat.service;

import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.bean.UdpServerNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UdpServerNodeManager {

    private Map<String,UdpServerNode> udpServerNodeMap = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void init(){
        //ps:如果
    }

    public void refresh(String udpServer, UdpServerNode node) {
        udpServerNodeMap.put(udpServer,node);
    }

    public UdpServerNode get(String udpServer) {
        return udpServerNodeMap.get(udpServer);
    }

    /**
     * 返回可用对udpServer列表
     * @return
     */
    public List<UdpServerNode> getValidUdpServerNodeList(){
        List<UdpServerNode> udpServerNodeList = new ArrayList<>();
        for (Map.Entry<String,UdpServerNode> entry:udpServerNodeMap.entrySet()){
            udpServerNodeList.add(entry.getValue());
        }
        return udpServerNodeList.parallelStream().filter(udpServerNode -> udpServerNode.getStatus() == 0).collect(Collectors.toList());
    }

}
