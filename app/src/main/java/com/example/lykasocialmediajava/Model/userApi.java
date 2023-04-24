package com.example.lykasocialmediajava.Model;




import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface userApi {


@POST("saveUser")
Call<Searchusermodel> apisaveUser(@Body Searchusermodel userApiInfo);

    @GET("getUser1")
    Call<List<Searchusermodel>> apigetUsers(@Query("text") String text);




    @POST("updateuser")
    Call<Searchusermodel> apiupdateuser(@Body Searchusermodel userApiInfo);






}
