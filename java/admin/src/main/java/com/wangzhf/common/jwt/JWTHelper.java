package com.wangzhf.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wangzhf.common.jwt.domain.JWTHeader;
import com.wangzhf.common.jwt.domain.JWTPayload;
import com.wangzhf.common.jwt.domain.JWTToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 生成和解析JWT
 */
public class JWTHelper {

    private static Logger logger = LoggerFactory.getLogger(JWTHelper.class);

    private static final String HMAC256_SECRET = "secret";

    /**
     * 使用HMAC256算法生成JWT Token
     * @param header
     * @param payload
     * @return
     */
    public String createHMAC256Token(JWTHeader header, JWTPayload payload) {
        Algorithm algorithm = Algorithm.HMAC256(HMAC256_SECRET);
        JWTCreator.Builder builder = JWT.create();
        if (header != null) {
            Map<String, Object> headerMap = header.getParams();
            if (!headerMap.isEmpty()) {
                builder.withHeader(headerMap);
            }
        }

        build(builder, payload);

        return builder.sign(algorithm);
    }

    /**
     * 解析HMAC256的Token
     * @param token
     * @param headerKeys
     * @return
     */
    public JWTToken getFromHMAC256Token(String token, String[] headerKeys) {
        Algorithm algorithm = Algorithm.HMAC256(HMAC256_SECRET);
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            logger.warn("Get from HMAC256 token: {} failed: {}", token, e.getMessage());
            return null;
        }
        return resolve(jwt, headerKeys);
    }

    /**
     * 解析参数
     * @param jwt
     * @param headerKeys
     * @return
     */
    private JWTToken resolve(DecodedJWT jwt, String[] headerKeys) {
        // header
        JWTHeader header = new JWTHeader();
        Claim algClaim = jwt.getHeaderClaim("alg");
        if (algClaim != null) {
            header.setAlg(algClaim.asString());
        }
        Claim typClaim = jwt.getHeaderClaim("typ");
        if (typClaim != null) {
            header.setTyp(typClaim.asString());
        }
        if (headerKeys != null && headerKeys.length > 0) {
            for (String key : headerKeys) {
                Claim keyClaim = jwt.getHeaderClaim(key);
                if (keyClaim != null) {
                    header.addParam(key, keyClaim.asString());
                }
            }
        }

        // payload
        JWTPayload payload = new JWTPayload();
        payload.setIss(jwt.getIssuer());
        payload.setExp(jwt.getExpiresAt());
        payload.setAud(jwt.getAudience());
        payload.setIat(jwt.getIssuedAt());
        payload.setJti(jwt.getId());
        payload.setNbf(jwt.getNotBefore());
        payload.setSub(jwt.getSubject());
        Map<String, Claim> claims = jwt.getClaims();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            String key = entry.getKey();
            Claim claim = entry.getValue();
            payload.addParam(key, claim.asString());
        }
        JWTToken token = new JWTToken();
        token.setHeader(header);
        token.setPayload(payload);
        return token;
    }

    /**
     * 封装参数
     * @param builder
     * @param payload
     */
    private void build(JWTCreator.Builder builder, JWTPayload payload) {
        if (payload.getIss() != null) {
            builder.withIssuer(payload.getIss());
        }
        if (payload.getAud() != null) {
            builder.withAudience(payload.getAud().toArray(new String[payload.getAud().size()]));
        }
        if (payload.getExp() != null ) {
            builder.withExpiresAt(payload.getExp());
        }
        if (payload.getIat() != null ) {
            builder.withExpiresAt(payload.getIat());
        }
        if (payload.getJti() != null) {
            builder.withJWTId(payload.getJti());
        }
        if (payload.getNbf() != null ) {
            builder.withNotBefore(payload.getNbf());
        }
        if (payload.getSub() != null) {
            builder.withSubject(payload.getSub());
        }
        Map<String, Object> payloadMap = payload.getParams();
        if (!payloadMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : payloadMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    builder.withClaim(key, (String) value);
                } else if (value instanceof Boolean) {
                    builder.withClaim(key, (Boolean) value);
                } else if (value instanceof Integer) {
                    builder.withClaim(key, (Integer) value);
                } else if (value instanceof Long) {
                    builder.withClaim(key, (Long) value);
                } else if (value instanceof Date) {
                    builder.withClaim(key, (Date) value);
                } else if (value instanceof Double) {
                    builder.withClaim(key, (Double) value);
                } else if (value instanceof String[]) {
                    builder.withArrayClaim(key, (String[]) value);
                } else if (value instanceof Integer[]) {
                    builder.withArrayClaim(key, (Integer[]) value);
                } else if (value instanceof Long[]) {
                    builder.withArrayClaim(key, (Long[]) value);
                } else {
                    throw new UnsupportedOperationException("Claims cannot support the type: " + value.getClass().getName());
                }
            }
        }
    }

}
