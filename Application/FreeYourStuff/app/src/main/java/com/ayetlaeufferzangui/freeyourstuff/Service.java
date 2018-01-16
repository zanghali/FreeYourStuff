package com.ayetlaeufferzangui.freeyourstuff;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by lothairelaeuffer on 10/01/2018.
 */

public interface Service {

    String ENDPOINT = "http://freeyourstuff.ddns.net:3000";

    @POST("/addItem")
    Call<String> addItem(@Body Item item);

    @FormUrlEncoded
    @POST("/getItemById")
    Call<List<Item>> getItemById(@Field("id_item") String query);

    @FormUrlEncoded
    @POST("/getUserByEmail")
    Call<List<User>> getUserByEmail(@Field("email") String query);

    @POST("/addUser")
    Call<String> addUser(@Body User user);

    @POST("/getItemList")
    Call<List<Item>> getItemList();

    @POST("/getItemByFilterGeo")
    Call<List<Item>> getItemByFilterGeo(@Body String body);

    @Multipart
    @POST("/upload")
    Call<String> uploadPhoto(@Part MultipartBody.Part photo, @Part("id_user") RequestBody id_user);

    @FormUrlEncoded
    @POST("/getItemByUser")
    Call<List<Item>> getItemByUser(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("/setUserInterestedByItem")
    Call<String> setUserInterestedByItem(@Field("id_user") String id_user, @Field("id_item") String id_item);

    @FormUrlEncoded
    @POST("/getUserInterestedByItem")
    Call<List<User>> getUserInterestedByItem(@Field("id_item") String id_item);
}
