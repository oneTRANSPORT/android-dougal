package com.interdigital.android.onem2msdk;

import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OneM2MService {

    // TODO Common headers should go into an interceptor?

    @Headers({
//            "Accept: application/json",
//            "User-Agent: OneM2M-Android",
            "X-M2M-RI: i4568g",
            "Content-Type: application/json; ty=" + Types.RESOURCE_TYPE_APPLICATION_ENTITY
    })
    @POST("{path}")
    Call<ResponseHolder> createAe(
            @Path("path") String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-Origin") String aeId,
            @Header("X-M2M-NM") String aeName,
            @Body RequestHolder requestHolder);

    @Headers({
//            "Accept: application/json",
//            "User-Agent: OneM2M-Android",
            "X-M2M-RI: 34543"
    })
    @GET("{path}")
    Call<ResponseHolder> retrieveAe(
            @Path("path") String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-Origin") String aeId);

    Void updateAe();

    @Headers({
//            "Accept: application/json",
//            "User-Agent: OneM2M-Android",
            "X-M2M-RI: 487bgkl"
    })
    @DELETE("{cseName}/{aeName}")
    Call<Void> deleteAe(
            @Path("cseName") String cseName,
            @Path("aeName") String aeName,
            @Header("Authorization") String authorization,
            @Header("X-M2M-Origin") String aeId
    );

}
