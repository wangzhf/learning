package com.wangzhf.auth.security.config.auth;

import com.google.gson.Gson;
import com.wabestway.common.domain.ResponseResult;
import com.wabestway.common.jwt.JWTHelper;
import com.wabestway.common.jwt.domain.JWTPayload;
import com.wabestway.common.jwt.domain.JWTToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理生成及解析jwt token
 */
public class TokenAuthenticationService {

    public static void addAuthentication(HttpServletResponse response, String username) {
        JWTHelper helper = new JWTHelper();
        JWTPayload payload = new JWTPayload();
        payload.addParam("name", username);
        payload.addParam("authorities", "ROLE_ADMIN,AUTH_WRITE");
        String token = helper.createHMAC256Token(null, payload);

        ResponseResult responseResult = new ResponseResult();
        responseResult.setStatus(HttpServletResponse.SC_OK);
        responseResult.setStatusText("success");
        Map<String, Object> ret = new HashMap<>();
        ret.put("token", token);
        responseResult.setData(ret);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getOutputStream().println(new Gson().toJson(responseResult).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            JWTHelper helper = new JWTHelper();
            JWTToken jwt = helper.getFromHMAC256Token(token, null);
            String name = (String) jwt.getPayload().getParam("name");
            String authorities = (String) jwt.getPayload().getParam("authorities");
            List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            if (StringUtils.isNotBlank(name)) {
                return new UsernamePasswordAuthenticationToken(name, null, list);
            }
        }
        return null;
    }

}
