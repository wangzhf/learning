package com.wangzhf.common.domain;

import java.io.Serializable;

/**
 * 封装响应结果model
 */
public class ResponseResult implements Serializable {

    private Object data;

    private Integer status;

    private String statusText;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
