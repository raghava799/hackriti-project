package com.alacriti.hackriti.utils.constants;


public class EnvVarConfig {
	public static String getOauthTokenServerEncodedUrl() {
        return "https://accounts.google.com/o/oauth2/token";
    }

    public static String getOauthClientId(){
        return "560429940835-i9l86ctcptqektdh3vhffsk0fom3hmio.apps.googleusercontent.com";
    }


    public static String getOauthClientSecret() {
        return "iZvbPnzRyWb7wpUXU4JQ1fRN";
    }


    public static String getOauthRedirectUri() {
        return "http://localhost:4203";
    }


    public static String getOauthUiLoginUrl() {
        return "http://localhost:4203";
    }

	public static Long getJwtTokenLifeSpan() {
		return Long.valueOf("1500000");
	}

	public static Long getJwtTokenRefreshGracePeriod() {
		return Long.valueOf("300000");
	}

}
