package com.qianli.ilink.cloud_platform.messagecenter.utils;

import java.util.UUID;

public class IdGenerater {

    public static String kafkaKey(){
        return System.currentTimeMillis()+ UUID.randomUUID().toString().substring(0,7);
    }

}
