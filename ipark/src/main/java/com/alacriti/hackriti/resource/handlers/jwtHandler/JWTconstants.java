package com.alacriti.hackriti.resource.handlers.jwtHandler;

import com.auth0.jwt.algorithms.*;

import java.io.*;

public class JWTconstants {

    private Algorithm algorithm;
    private String authHeader;
    private String authHeaderPerfix;

    public JWTconstants() throws UnsupportedEncodingException {
        this.algorithm = Algorithm.HMAC256("camila");
        this.authHeader = "Authorization";
        this.authHeaderPerfix = "Basic ";
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public String getAuthHeaderPerfix() {
        return authHeaderPerfix;
    }
}
