package com.interdigital.android.dougal;

import com.interdigital.android.dougal.network.request.RequestHolder;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.Resource;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DougalService {

    @POST("{path}")
    Call<ResponseHolder> create(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-NM") String aeName,
            @Header("Content-Type") String contentType,
            @Header("X-M2M-RI") String requestId,
            @Query("rt") @Resource.ResponseType int responseType,
            @Body RequestHolder requestHolder);

    @GET("{path}")
    Call<ResponseHolder> retrieve(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-RI") String requestId,
            @Query("rt") @Resource.ResponseType int responseType,
            @QueryMap Map<String, String> queryMap);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("{path}")
    Call<ResponseHolder> update(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-RI") String requestId,
            @Query("rt") @Resource.ResponseType int responseType,
            @QueryMap Map<String, String> queryMap,
            @Body RequestHolder requestHolder);

    @DELETE("{path}")
    Call<Void> delete(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-RI") String requestId,
            @Query("rt") @Resource.ResponseType int responseType,
            @QueryMap Map<String, String> queryMap);

    @GET("{path}?fu=1")
    Call<ResponseHolder> discover(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-RI") String requestId,
            @Query("rt") @Resource.ResponseType int responseType,
            @QueryMap Map<String, String> queryMap);
}
