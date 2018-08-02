package com.qianli.ilink.cloud_platform.commons.core.utils;

import com.qianli.ilink.cloud_platform.commons.core.eneity.ResponseEntity;
import com.qianli.ilink.cloud_platform.commons.core.enums.ResultEnum;

public class ResponseEntityUtils {

    public static ResponseEntity builderSuccess(Object data) {
        return build(ResultEnum.SUCCESS,data);
    }

    public static ResponseEntity builderSuccess() {
        return build(ResultEnum.SUCCESS);
    }

    public static ResponseEntity build(ResultEnum resultEnum,Object data) {
        return buildEmpty().setErrorCode(resultEnum.getCode()).setMsg(resultEnum.getMsg()).setDetails(resultEnum.getDetails()).setData(data);
    }

    public static ResponseEntity build(ResultEnum resultEnum) {
        return build(resultEnum,null);
    }

    public static ResponseEntity buildEmpty() {

        return new ResponseEntity();
    }

}
