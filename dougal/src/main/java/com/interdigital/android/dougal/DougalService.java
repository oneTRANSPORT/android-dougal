package com.interdigital.android.dougal;

import com.interdigital.android.dougal.network.request.RequestHolder;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DougalService {

    @POST("{path}")
    Call<ResponseHolder> create(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-NM") String aeName,
            @Header("Content-Type") String contentType,
            @Header("X-M2M-RI") String requestId,
            @Body RequestHolder requestHolder);

    @GET("{path}")
    Call<ResponseHolder> retrieve(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-RI") String requestId);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("{path}")
    Call<ResponseHolder> update(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-RI") String requestId,
            @Body RequestHolder requestHolder);

    @DELETE("{path}")
    Call<Void> delete(
            @Header("X-M2M-Origin") String aeId,
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-RI") String requestId);
}
