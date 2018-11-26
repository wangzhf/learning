package com.wangzhf.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wangzhf.common.jwt.domain.JWTHeader;
import com.wangzhf.common.jwt.domain.JWTPayload;
import com.wangzhf.common.jwt.domain.JWTToken;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtTest {

    @Test
    public void testCreateTokenWithHMAC256() {
        Map<String, Object> header = new HashMap<>();
        header.put("application", "json");
        header.put("encoding", "UTF-8");
        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        String token = JWT.create()
            .withIssuer("wangzhf")
            .withHeader(header)
            .withAudience("audience")
            .withClaim("name", "zhangsan")
            .withClaim("age", 20)
            .withExpiresAt(DateUtils.addHours(new Date(), 2))
            .withIssuedAt(new Date())
            .withJWTId("1111")
            .withKeyId("2222")
            .withNotBefore(new Date())
            .withSubject("aaaa")
            .sign(algorithmHS);
        System.out.println(token);
    }

    @Test
    public void testCreateTokenWithHMAC2562() {
        JWTHeader header = new JWTHeader();
        header.addParam("application", "JSON");
        JWTPayload payload = new JWTPayload();
        payload.setExp(DateUtils.addHours(new Date(), 2));
        payload.setIss("wangzhf");
        payload.addParam("username", "zhangsan");
        payload.addParam("age", 20);
        JWTHelper helper = new JWTHelper();
        String token = helper.createHMAC256Token(header, payload);
        System.out.println(token);
    }

    @Test
    public void testVerifyTokenWithHMAC256() {
        String token = "eyJ0eXAiOiJKV1QiLCJhcHBsaWNhdGlvbiI6Impzb24iLCJlbmNvZGluZyI6IlVURi04IiwiYWxnIjoiSFMyNTYiLCJraWQiOiIyMjIyIn0.eyJhdWQiOiJhdWRpZW5jZSIsInN1YiI6ImFhYWEiLCJuYmYiOjE1NDI1NDMyODMsImlzcyI6Indhbmd6aGYiLCJuYW1lIjoiemhhbmdzYW4iLCJleHAiOjE1NDI1NTA0ODMsImlhdCI6MTU0MjU0MzI4MywiYWdlIjoyMCwianRpIjoiMTExMSJ9.hBNGhvzPIkzq7mDCK8OCMVJGH9zH554uQGKiAWV8JpY";
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("wangzhf")
            .build();
        DecodedJWT jwt = verifier.verify(token);

        String algo = jwt.getAlgorithm();
        List<String> audiences = jwt.getAudience();
        Map<String, Claim> claims = jwt.getClaims();
        String contentType = jwt.getContentType();
        Date expireAt = jwt.getExpiresAt();
        String id = jwt.getId();
        Date issueAt = jwt.getIssuedAt();
        String issuer = jwt.getIssuer();
        String keyId = jwt.getKeyId();
        Date notBefore = jwt.getNotBefore();
        String subject = jwt.getSubject();
        Claim header = jwt.getHeaderClaim("application");
        String type = jwt.getType();
        System.out.printf("algo: %s\n", algo);
        System.out.println("audiences: " + audiences);
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            String key = entry.getKey();
            Claim claim = entry.getValue();
            System.out.println("claims: " + key + ", claim: " + claim.asString());
        }
        System.out.printf("contentType: %s\n", contentType);
        System.out.println("expreAt: " + expireAt);
        System.out.printf("id: %s\n", id);
        System.out.println("issueAt: " + issueAt);
        System.out.printf("keyId: %s\n", keyId);
        System.out.println("notBefore: " + notBefore);
        System.out.printf("subject: %s\n", subject);
        System.out.println("header: " + header.asString());
        System.out.printf("type: %s\n", type);
    }

    @Test
    public void testVerifyTokenWithHMAC2562() {
        String token = "eyJhcHBsaWNhdGlvbiI6IkpTT04iLCJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ3YW5nemhmIiwiZXhwIjoxNTQyNTUxOTQxLCJhZ2UiOjIwLCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.FHFxNQ9y28OjrTVqSxSn22lzKMbluhBIPfT39mZhsts";
        JWTHelper helper = new JWTHelper();
        JWTToken jwt =  helper.getFromHMAC256Token(token, new String[]{"application"});
        System.out.println(jwt);
    }

    @Test
    public void testCreateTokenWithRSA() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = kpg.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        System.out.println(publicKey);
        System.out.println(privateKey);
    }
}
