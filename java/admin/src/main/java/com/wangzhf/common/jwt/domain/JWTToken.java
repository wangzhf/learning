package com.wangzhf.common.jwt.domain;

/**
 * JWT: JSON Web Token
 * 包含三个部分：
 *      - Header
 *      - Payload
 *      - Signature
 * 以逗号分割，形式类似： `xxxxx.yyyyy.zzzzz`
 *
 * ## Header
 * Header部分通常包含两部分：
 *      - Token的类型（typ）：一般为JWT
 *      - 哈希算法（alg）：使用的哈希算法，例如HMAC、SHA256或RSA等
 *  example：
 *  ```
 *  { "alg": "HS256", "typ": "JWT" }
 *  ```
 *  然后，将该JSON使用base64编码之后作为JWT的第一部分。
 *
 * ## Payload
 * Payload是JWT的第二部分，包含了`claims`，Claims用来声明实体及额外的数据，分为三种类型：
 *      - Registered claims：该部分是预先定义的变量，包含以下几种：
 *          > "iss" (Issuer) Claim: The "iss" (issuer) claim identifies the principal that issued the
 *              JWT.  The processing of this claim is generally application specific.
 *              The "iss" value is a case-sensitive string containing a StringOrURI
 *              value.  Use of this claim is OPTIONAL.
 *          > "sub" (Subject) Claim: The "sub" (subject) claim identifies the principal that is the
 *              subject of the JWT.  The claims in a JWT are normally statements
 *              about the subject.  The subject value MUST either be scoped to be
 *              locally unique in the context of the issuer or be globally unique.
 *              The processing of this claim is generally application specific.  The
 *              "sub" value is a case-sensitive string containing a StringOrURI
 *              value.  Use of this claim is OPTIONAL.
 *          > "aud" (Audience) Claim: The "aud" (audience) claim identifies the recipients that the JWT is
 *              intended for.  Each principal intended to process the JWT MUST
 *              identify itself with a value in the audience claim.  If the principal
 *              processing the claim does not identify itself with a value in the
 *              "aud" claim when this claim is present, then the JWT MUST be
 *              rejected.  In the general case, the "aud" value is an array of case-
 *              sensitive strings, each containing a StringOrURI value.  In the
 *              special case when the JWT has one audience, the "aud" value MAY be a
 *              single case-sensitive string containing a StringOrURI value.  The
 *              interpretation of audience values is generally application specific.
 *              Use of this claim is OPTIONAL.
 *          > "exp" (Expiration Time) Claim: The "exp" (expiration time) claim identifies the expiration time on
 *              or after which the JWT MUST NOT be accepted for processing.  The
 *              processing of the "exp" claim requires that the current date/time
 *              MUST be before the expiration date/time listed in the "exp" claim.
 *              Implementers MAY provide for some small leeway, usually no more than
 *              a few minutes, to account for clock skew.  Its value MUST be a number
 *              containing a NumericDate value.  Use of this claim is OPTIONAL.
 *          > "nbf" (Not Before) Claim: The "nbf" (not before) claim identifies the time before which the JWT
 *              MUST NOT be accepted for processing.  The processing of the "nbf"
 *              claim requires that the current date/time MUST be after or equal to
 *              the not-before date/time listed in the "nbf" claim.  Implementers MAY
 *              provide for some small leeway, usually no more than a few minutes, to
 *              account for clock skew.  Its value MUST be a number containing a
 *              NumericDate value.  Use of this claim is OPTIONAL.
 *          > "iat" (Issued At) Claim: The "iat" (issued at) claim identifies the time at which the JWT was
 *              issued.  This claim can be used to determine the age of the JWT.  Its
 *              value MUST be a number containing a NumericDate value.  Use of this
 *              claim is OPTIONAL.
 *          > "jti" (JWT ID) Claim: The "jti" (JWT ID) claim provides a unique identifier for the JWT.
 *              The identifier value MUST be assigned in a manner that ensures that
 *              there is a negligible probability that the same value will be
 *              accidentally assigned to a different data object; if the application
 *              uses multiple issuers, collisions MUST be prevented among values
 *              produced by different issuers as well.  The "jti" claim can be used
 *              to prevent the JWT from being replayed.  The "jti" value is a case-
 *              sensitive string.  Use of this claim is OPTIONAL.
 *
 *      - Public claims:
 *
 *
 */
public class JWTToken {

    private JWTHeader header;

    private JWTPayload payload;

    private JWTSignature signature;

    public JWTHeader getHeader() {
        return header;
    }

    public void setHeader(JWTHeader header) {
        this.header = header;
    }

    public JWTPayload getPayload() {
        return payload;
    }

    public void setPayload(JWTPayload payload) {
        this.payload = payload;
    }

    public JWTSignature getSignature() {
        return signature;
    }

    public void setSignature(JWTSignature signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "JWTToken{" +
            "header=" + header +
            ", payload=" + payload +
            ", signature=" + signature +
            '}';
    }
}
