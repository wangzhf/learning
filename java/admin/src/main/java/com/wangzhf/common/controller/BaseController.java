package com.wangzhf.common.controller;

import com.wangzhf.common.domain.ResponseResult;
import com.wangzhf.common.util.CommonConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseController {

    private static final List EMPTY_LIST = new ArrayList(0);

    private static final Object EMPTY_OBJECT = new HashMap<>();

    public ResponseResult handleSuccessObject(Object obj) {
        ResponseResult result = new ResponseResult();
        result.setData(obj == null ? EMPTY_OBJECT : obj);
        result.setStatus(CommonConstant.Http.STATUS_OK.getStatus());
        result.setStatusText(CommonConstant.Http.STATUS_OK.getMessage());
        return result;
    }

    public ResponseResult handleErrorObject(Object obj) {
        ResponseResult result = new ResponseResult();
        result.setData(obj == null ? EMPTY_OBJECT : obj);
        result.setStatus(CommonConstant.Http.EX_OTHER_CODE.getStatus());
        result.setStatusText(CommonConstant.Http.EX_OTHER_CODE.getMessage());
        return result;
    }

    public ResponseResult handleSuccessList(List list) {
        ResponseResult result = new ResponseResult();
        result.setData(list == null ? EMPTY_LIST : list);
        result.setStatus(CommonConstant.Http.STATUS_OK.getStatus());
        result.setStatusText(CommonConstant.Http.STATUS_OK.getMessage());
        return result;
    }

    public ResponseResult handleErrorList(List list) {
        ResponseResult result = new ResponseResult();
        result.setData(list == null ? EMPTY_LIST : list);
        result.setStatus(CommonConstant.Http.EX_OTHER_CODE.getStatus());
        result.setStatusText(CommonConstant.Http.EX_OTHER_CODE.getMessage());
        return result;
    }

}
