package com.qianli.ilink.cloud_platform.messagecenter.web;

import com.googlecode.protobuf.format.JsonFormat;
import com.qianli.ilink.cloud_platform.messagecenter.enums.MessageType;
import com.qianli.ilink.cloud_platform.messagecenter.pojo.dto.*;
import com.qianli.ilink.cloud_platform.messagecenter.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApInfoController {

    @Autowired
    private MessageSender messageSender;

    @RequestMapping(value = "/apbaseinfo", method = RequestMethod.POST)
    public void apBaseInfoReceive(@RequestBody ApBaseInfoProto.ApBaseInfo apBaseInfo) {
        if(apBaseInfo == null){

        }
        messageSender.send(Message.builder().type(MessageType.AP_BASE_INFO.getValue()).body(new JsonFormat().printToString(apBaseInfo)).build());
    }

    @RequestMapping(value = "/apstatusinfo", method = RequestMethod.POST)
    public void apStatusInfoReceive(@RequestBody ApStatusInfoProto.ApStatusInfo apStatusInfo) {
        if(apStatusInfo == null){

        }
        messageSender.send(Message.builder().type(MessageType.AP_STATUS_INFO.getValue()).body(new JsonFormat().printToString(apStatusInfo)).build());
    }

    @RequestMapping(value = "/aponlinestainfo", method = RequestMethod.POST)
    public void apOnlineStaInfoReceive(@RequestBody ApOnlineStaInfoProto.TotalApOnlineStaInfo totalApOnlineStaInfo) {
        if(totalApOnlineStaInfo == null){

        }
        messageSender.send(Message.builder().type(MessageType.AP_ONLINE_STA_INFO.getValue()).body(new JsonFormat().printToString(totalApOnlineStaInfo)).build());
    }

    @RequestMapping(value = "/apofflinestainfo", method = RequestMethod.POST)
    public void apOfflineStaInfoReceive(@RequestBody ApOfflineStaInfoProto.TotalApOfflineStaInfo totalApOfflineStaInfo) {
        if(totalApOfflineStaInfo == null){

        }
        messageSender.send(Message.builder().type(MessageType.AP_OFFLINE_STA_INFO.getValue()).body(new JsonFormat().printToString(totalApOfflineStaInfo)).build());
    }

}
