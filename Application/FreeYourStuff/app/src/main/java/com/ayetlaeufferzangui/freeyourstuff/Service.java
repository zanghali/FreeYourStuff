package com.ayetlaeufferzangui.freeyourstuff;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.Message;
import com.ayetlaeufferzangui.freeyourstuff.Model.NbOfInterestedPeople;
import com.ayetlaeufferzangui.freeyourstuff.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by lothairelaeuffer on 10/01/2018.
 */

public interface Service {

    String ENDPOINT = "http://freeyourstuff.ddns.net:3000";


    @POST("/addUser")
    Call<String> addUser(@Body User user);

    @FormUrlEncoded
    @POST("/getUserByEmail")
    Call<List<User>> getUserByEmail(@Field("email") String query);

    @FormUrlEncoded
    @POST("/getUserById")
    Call<List<User>> getUserById(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("/getUserInterestedByItem")
    Call<List<User>> getUserInterestedByItem(@Field("id_item") String id_item);

    @POST("/updateUser")
    Call<String> updateUser(@Body User user);

    @POST("/addItem")
    Call<String> addItem(@Body Item item);
    //Call<String> addItem(@Header("Authorization") String authorization,@Body Item item);

    @FormUrlEncoded
    @POST("/getItemById")
    Call<List<Item>> getItemById(@Field("id_item") String query);

    @POST("/getItemList")
    Call<List<Item>> getItemList();

    @FormUrlEncoded
    @POST("/getItemByFilterGeo")
    Call<List<Item>> getItemByFilterGeo(@Field("gps") String gps, @Field("distance") String distance);

    @Multipart
    @POST("/upload")
    Call<String> uploadPhoto(@Part MultipartBody.Part photo, @Part("id_user") RequestBody id_user);

    @FormUrlEncoded
    @POST("/getItemByUser")
    Call<List<Item>> getItemByUser(@Field("id_user") String id_user, @Field("gps") String gps);

    @FormUrlEncoded
    @POST("/getItemOfUserInterestedBy")
    Call<List<Item>> getItemOfUserInterestedBy(@Field("id_user") String id_user, @Field("gps") String gps);

    @FormUrlEncoded
    @POST("/setUserInterestedByItem")
    Call<String> setUserInterestedByItem(@Field("id_user") String id_user, @Field("id_item") String id_item);


    @FormUrlEncoded
    @POST("/getNumberInterestedByItem")
    Call<List<NbOfInterestedPeople>> getNumberInterestedByItem(@Field("id_item") String id_item);

    @FormUrlEncoded
    @POST("/deleteUserInterestedByItem")
    Call<String> deleteUserInterestedByItem(@Field("id_user") String id_user, @Field("id_item") String id_item);

    @FormUrlEncoded
    @POST("/getItemByFilterCategory")
    Call<List<Item>> getItemByFilterCategory(@Field("category") String category);

    @FormUrlEncoded
    @POST("/deleteItem")
    Call<String> deleteItem(@Field("id_item") String id_item, @Field("photo") String photo, @Field("id_user") String id_user );

    @FormUrlEncoded
    @POST("/getItemByKeywords")
    Call<List<Item>> getItemByKeywords(@Field("gps") String gps, @Field("distance") String distance, @Field("keywords") String keywords);

    @FormUrlEncoded
    @POST("/getChat")
    Call<List<Message>> getChat(@Field("id_item") String id_item, @Field("first_person") String first_person, @Field("second_person") String second_person);

    @POST("/addChat")
    Call<String> addChat(@Body Message message);

    @FormUrlEncoded
    @POST("/updateItemStatus")
    Call<String> updateItemStatus(@Field("id_user") String id_user, @Field("id_userInterestedBy") String id_userInterestedBy, @Field("id_item") String id_item);

}