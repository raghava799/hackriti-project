package com.alacriti.hackriti.utils.constants;


public class EnvVarConfig {
	public static String getOauthTokenServerEncodedUrl() {
        return "https://accounts.google.com/o/oauth2/token";
    }

    public static String getOauthClientId(){
        return "799023887043-blv26lmp2q5ml00ti587q3bum508ks2a.apps.googleusercontent.com";
    }


    public static String getOauthClientSecret() {
        return "bm7mREEzRnukgCYqTgcg_BsX";
    }


    public static String getOauthRedirectUri() {
        return "https://dnpjnivbi5kz1.cloudfront.net";
    }


    public static String getOauthUiLoginUrl() {
        return "https://dnpjnivbi5kz1.cloudfront.net";
    }

	public static Long getJwtTokenLifeSpan() {
		return Long.valueOf("1500000");
	}

	public static Long getJwtTokenRefreshGracePeriod() {
		return Long.valueOf("300000");
	}

}
