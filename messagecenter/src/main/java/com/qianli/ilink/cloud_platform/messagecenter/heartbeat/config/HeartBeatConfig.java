package com.qianli.ilink.cloud_platform.messagecenter.heartbeat.config;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "heartbeat")
@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeartBeatConfig {

    String udpServerList;

    Integer retryTimes;


}
