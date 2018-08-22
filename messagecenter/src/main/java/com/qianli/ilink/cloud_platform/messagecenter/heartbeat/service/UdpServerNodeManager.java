package com.qianli.ilink.cloud_platform.messagecenter.heartbeat.service;

import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.bean.UdpServerNode;
import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.config.HeartBeatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UdpServerNodeManager {

    @Autowired
    private HeartBeatConfig heartBeatConfig;

    /**
     * port对应的UdpServerNodes
     */
    private Map<String,UdpServerNode> udpServerNodeMap = new ConcurrentHashMap<>();

    protected void refresh(String udpServer, UdpServerNode node) {
        udpServerNodeMap.put(udpServer,node);
    }

    protected UdpServerNode get(String udpServer) {
        return udpServerNodeMap.get(udpServer);
    }

    /**
     * 返回可用对udpServer列表
     * @return
     */
    protected List<UdpServerNode> getValidUdpServerNodeList(){
        List<UdpServerNode> udpServerNodeList = new ArrayList<>();
        for (Map.Entry<String,UdpServerNode> entry:udpServerNodeMap.entrySet()){
            udpServerNodeList.add(entry.getValue());
        }
        return udpServerNodeList.parallelStream().filter(udpServerNode -> udpServerNode.getStatus() == 0).collect(Collectors.toList());
    }

    public List<String> getValidUdpServerNodes(){
        List<UdpServerNode> validUdpServerNodeList = getValidUdpServerNodeList();
        return validUdpServerNodeList.parallelStream().map(udpServerNode -> udpServerNode.getIp()+":"+udpServerNode.getPort()).collect(Collectors.toList());
    }

    /**
     * 返回可用udpServerIp 部署多个节点port需要一致
     * @param ip
     * @return
     */
    public String getValidUdpServerNode(String ip){
        List<UdpServerNode> validUdpServerNodeList = getValidUdpServerNodeList();
        /**
         * 根据ip找不到对应的UdpServerNode 说明死绝了。
         */
        List<UdpServerNode> udpServerNodes = validUdpServerNodeList.parallelStream().filter(udpServerNode -> ip.equals(udpServerNode.getIp())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(udpServerNodes)){
            return null;
        }
        /**
         * 找到对应的ip则返回，否则从可以列表选择第一个。
         */
        Optional<UdpServerNode> udpServerNodeOptional = udpServerNodes.parallelStream().filter(udpServerNode -> (udpServerNode.getIp()).equals(ip)).findFirst();
        if(udpServerNodeOptional.isPresent()){
            return ip;
        }else{
            return udpServerNodes.get(0).getIp();
        }

    }

    public void refreshWithSuccess(String udpServer) {
        String ip = udpServer.split(":")[0];
        Integer port = Integer.parseInt(udpServer.split(":")[1]);
        //如果成功，强制刷新node.
        refresh(udpServer,UdpServerNode.builder().ip(ip).port(port).retryTimes(0).status(0).build());
    }

    public void refreshWithFail(String udpServer) {
        Integer retryTimes = heartBeatConfig.getRetryTimes();
        String ip = udpServer.split(":")[0];
        Integer port = Integer.parseInt(udpServer.split(":")[1]);
        //如果失败，先判断是否含有node
        UdpServerNode node = get(udpServer);
        //如果包含node,则对失败次数加1 ps:失败3次以内认为有效
        if(node == null){
            //因为有node初始化,这段逻辑可能不会被执行
            refresh(udpServer,UdpServerNode.builder().ip(ip).port(port).retryTimes(1).status(0).build());
            log.warn("node {}:{} retry {} times...",ip,port,1);
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
                log.warn("node {}:{} retry {} times...",ip,port,currentRetryTimes);
            }
            refresh(udpServer,node);
        }
    }
}
