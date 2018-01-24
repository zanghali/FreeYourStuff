package com.ayetlaeufferzangui.freeyourstuff;

/**
 * Created by lothairelaeuffer on 23/01/2018.
 */

public class AccessTokenModel {

    private String access_token;
    private String expires_in;
    private String token_type;

    public AccessTokenModel(String access_token, String expires_in, String token_type) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
    }

    @Override
    public String toString() {
        return "AccessTokenModel{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", token_type='" + token_type + '\'' +
                '}';
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getToken_type() {
        return token_type;
    }
}
