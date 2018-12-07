package com.alacriti.hackriti.resource.handlers.jwtHandler;

import com.alacriti.hackriti.utils.constants.EnvVarConfig;
import com.alacriti.hackriti.vo.UserTokenData;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;

import java.io.*;
import java.util.*;

public class JWThandler {

    public static final String ISSUER = "alacriti";
    public static final String CLAIM_NAME = "name";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_CLIENT_KEY = "client_key";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";
    public static final String AUTH_HEADER_PREFIX = "Basic ";


    public String newToken(UserTokenData user) throws UnsupportedEncodingException {
        String token = null;
        try {
            Algorithm algorithm = new JWTconstants().getAlgorithm();
            Date issuetime = new Date();
            long t1 = issuetime.getTime();
            Date expirationTime = new Date(t1 + EnvVarConfig.getJwtTokenLifeSpan()); //after 50 minutes
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(CLAIM_NAME,user.getName())
                    .withClaim(CLAIM_USERNAME,user.getUserName())
                    .withClaim(CLAIM_CLIENT_KEY,user.getClientKey())
                    .withClaim(CLAIM_EMAIL,user.getEmailId())
                    .withClaim(CLAIM_ROLE,user.getRole())
                    .withIssuedAt(issuetime)
                    .withExpiresAt(expirationTime)
                    .sign(algorithm);
            token =  AUTH_HEADER_PREFIX + token;
        } catch (UnsupportedEncodingException | JWTCreationException exception){
            System.out.println(exception);
            throw exception;
        }
        return  token;
    }

    public String renewToken(String token){
        if (token == null || !token.isEmpty()) return null;
        String newToken = null;
        UserTokenData user = new UserTokenData();
        Date expirationTime;
        try {
            token = token.replaceFirst(AUTH_HEADER_PREFIX,"");
            DecodedJWT jwt = JWT.decode(token);
            user.setName(jwt.getClaim(CLAIM_NAME).asString());
            user.setUserName(jwt.getClaim(CLAIM_USERNAME).asString());
            user.setClientKey(jwt.getClaim(CLAIM_CLIENT_KEY).asString());
            user.setEmailId(jwt.getClaim(CLAIM_EMAIL).asString());
            user.setRole(jwt.getClaim(CLAIM_ROLE).asString());
            expirationTime = jwt.getExpiresAt();
            long milisecond = (new Date()).getTime() - expirationTime.getTime();
            if(milisecond < EnvVarConfig.getJwtTokenRefreshGracePeriod()) newToken = newToken(user); // 10 minute
        } catch (JWTCreationException | UnsupportedEncodingException exception){
            System.out.println(exception.getMessage());
        }
        return newToken;
    }

    public UserTokenData getUserData(String token){
        UserTokenData user = new UserTokenData();
        try {
            token = token.replaceFirst(AUTH_HEADER_PREFIX,"");
            DecodedJWT jwt = JWT.decode(token);
            user.setName(jwt.getClaim(CLAIM_NAME).asString());
            user.setUserName(jwt.getClaim(CLAIM_USERNAME).asString());
            user.setClientKey(jwt.getClaim(CLAIM_CLIENT_KEY).asString());
            user.setEmailId(jwt.getClaim(CLAIM_EMAIL).asString());
            user.setRole(jwt.getClaim(CLAIM_ROLE).asString());
        } catch (JWTDecodeException exception){
            System.out.println(exception.getMessage());
        }
        return user;
    }

    public boolean varifyToken(String token) {
        boolean isVarified = false;
        try {
            token = token.replaceFirst(AUTH_HEADER_PREFIX,"");
            Algorithm algorithm = new JWTconstants().getAlgorithm();
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            isVarified = true;
        } catch (UnsupportedEncodingException | JWTVerificationException exception){
            System.out.println(exception);
        }
        return isVarified;
    }
}
