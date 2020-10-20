package org.jmqtt.manage.config.permission;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

public class TokenUtil {

    private static final String SECRET = "jmqtt_3.0";

    public static String token(String userId, String username, String[] roleIds, long exp) {
        Date date = new Date(System.currentTimeMillis() + exp);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTCreator.Builder builder = JWT.create()
                .withClaim("username", username)
                .withClaim("userId", userId)
                .withArrayClaim("roleIds", roleIds);
        if (exp > 0) {
            return builder.withExpiresAt(date)
                    .sign(algorithm);
        }
        return builder.sign(algorithm);
    }

    public static boolean verify(String token, String username, String userId, String[] roleIds, long exp) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Verification verification = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("userId", userId)
                    .withArrayClaim("roleIds", roleIds);
            JWTVerifier verifier;
            if (exp > 0) {
                verifier = verification.acceptIssuedAt(System.currentTimeMillis()).build();
            } else {
                verifier = verification.build();
            }
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static Token getToken(String token) {
        DecodedJWT decode = JWT.decode(token);
        Token t = new Token();
        t.setExp(decode.getClaim("exp").asLong());
        t.setUsername(decode.getClaim("username").asString());
        t.setUserId(decode.getClaim("userId").asString());
        t.setRoleIds(decode.getClaim("roleIds").asArray(String.class));
        t.setTokenStr(token);
        return t;
    }

    public static void main(String[] args) throws Exception {
        String token = TokenUtil.token("user_id","yance",null,5 * 1000);
        Thread.sleep(5000);
        Token token1 = getToken(token);
        boolean verify = verify(token, "yance", token1.getUserId(),null,5 * 1000);
        System.out.println(verify);
        System.out.println(token1);
    }
}