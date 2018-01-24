package com.ayetlaeufferzangui.freeyourstuff;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lothairelaeuffer on 23/01/2018.
 */

public interface ServiceOAuth {

    String ENDPOINT_OAuth = "https://freeyourstuff.eu.auth0.com";


    @FormUrlEncoded
    @POST("/oauth/token")
    Call<AccessTokenModel> test(@Field("client_id") String client_id
            , @Field("client_secret") String client_secret
            , @Field("audience") String audience
            , @Field("grant_type") String grant_type
    );
}
