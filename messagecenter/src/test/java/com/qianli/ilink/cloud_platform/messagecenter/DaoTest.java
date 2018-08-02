package com.qianli.ilink.cloud_platform.messagecenter;

import com.qianli.ilink.cloud_platform.messagecenter.dao.ActIdInfoMapper;
import com.qianli.ilink.cloud_platform.messagecenter.dao.TestMapper;
import org.junit.Test;

import javax.annotation.Resource;

public class DaoTest extends BaseTest{

    @Resource
    private TestMapper testMapper;
    @Resource
    private ActIdInfoMapper actIdInfoMapper;
    @Test
    public void count(){

        //System.out.println(actIdInfoMapper.count());
        System.out.println(actIdInfoMapper.findByUsernameAndPassword("2","2"));
    }


}
