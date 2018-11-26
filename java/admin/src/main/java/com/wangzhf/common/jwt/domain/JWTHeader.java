package com.wangzhf.common.jwt.domain;

import java.util.HashMap;
import java.util.Map;

public class JWTHeader {

    private String alg;

    private String typ;

    private Map<String, Object> params = new HashMap<>();

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    public Object getParam(String key) {
        return this.params.get(key);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "JWTHeader{" +
            "alg='" + alg + '\'' +
            ", typ='" + typ + '\'' +
            ", params=" + params +
            '}';
    }
}
