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
Call<userApiInfo> apisaveUser(@Body userApiInfo userApiInfo);

    @GET("/getUser1")
    Call<List<userApiInfo>> apigetUsers(@Query("text") String text);




    @POST("updateuser")
    Call<userApiInfo> apiupdateuser(@Body userApiInfo userApiInfo);






}
