package com.qianli.ilink.cloud_platform.messagecenter.heartbeat.schedule;

import com.qianli.ilink.cloud_platform.messagecenter.heartbeat.service.HeartBeatSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeartBeatSchedule {

    @Autowired
    private HeartBeatSender heartBeatManager;

    /**
     * 每次执行任务之后5S再次执行该任务。
     */
    @Scheduled(fixedDelay = 5000)
    public void scheduleTask(){
        log.info("heartBeat schedule task start...");
        heartBeatManager.execute();
        log.info("heartBeat schedule task end...");
    }

}
