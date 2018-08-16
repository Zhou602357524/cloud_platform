package com.qianli.ilink.cloud_platform.messagecenter.heartbeat.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UdpServerNode {

    String ip;
    Integer port;
    Integer retryTimes;

    /**
     * 0 有效
     * 1 无效
     */
    Integer status;

}
