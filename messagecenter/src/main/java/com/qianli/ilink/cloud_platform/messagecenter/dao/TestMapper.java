package com.qianli.ilink.cloud_platform.messagecenter.dao;

import com.qianli.ilink.cloud_platform.commons.core.dao.BastMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TestMapper extends BastMapper{

    @Override
    @Select("select count(1) from act_id_info")
    int count();

    @Override
    @Select("select * from act_id_info")
    List<Map> getList();

    @Override
    @Select("select * from act_id_info where id = #{id}")
    Map getById(@Param("id") long id);

}
