package com.codehasy.chatg.Fragments;

import com.codehasy.chatg.Notifications.MyResponse;
import com.codehasy.chatg.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                        "Content-Type:application/json",
                        "Authorization:key=AAAAQQZUpx4:APA91bHGFjHw76UWkXMPRcsNZdEl7EQg1Z63eVe9dFBd-Rul3o_ugR1OKfeQZJsbCpTY7bakVM9HjBPgZtixJwy9gks6obhLIjAghbwQOlkVOl42i-5rjKVPGVdXBg1YP5w8kTkxuoOA"
}
    )

    @POST("fcm/send")
Call<MyResponse> sendNotification(@Body Sender body);
}
