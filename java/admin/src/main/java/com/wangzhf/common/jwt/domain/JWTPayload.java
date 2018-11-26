package com.wangzhf.common.jwt.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * refer: https://tools.ietf.org/html/rfc7519#section-4.1
 */
public class JWTPayload {

    // issuer
    private String iss;
    // Subject
    private String sub;
    // Audience
    private List<String> aud;
    // Expiration Time
    private Date exp;
    // Not Before
    private Date nbf;
    // Issued At
    private Date iat;
    // JWT ID
    private String jti;

    // comstum params
    private Map<String, Object> params = new HashMap<>();

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public List<String> getAud() {
        return aud;
    }

    public void setAud(List<String> aud) {
        this.aud = aud;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public Date getNbf() {
        return nbf;
    }

    public void setNbf(Date nbf) {
        this.nbf = nbf;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
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
        return "JWTPayload{" +
            "iss='" + iss + '\'' +
            ", sub='" + sub + '\'' +
            ", aud=" + aud +
            ", exp=" + exp +
            ", nbf=" + nbf +
            ", iat=" + iat +
            ", jti='" + jti + '\'' +
            ", params=" + params +
            '}';
    }
}
