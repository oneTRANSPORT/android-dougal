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

public interface OneM2MService {

    @Headers({
            "X-M2M-RI: i4568g",
            "Content-Type: application/json; ty=" + Types.RESOURCE_TYPE_APPLICATION_ENTITY
    })
    @POST("{path}")
    Call<ResponseHolder> createAe(
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-Origin") String aeId,
            @Header("X-M2M-NM") String aeName,
            @Body RequestHolder requestHolder);

    // TODO Generic retrieve?
    @Headers({
            "X-M2M-RI: 34543"
    })
    @GET("{path}")
    Call<ResponseHolder> retrieveAe(
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-Origin") String aeId);

    @Headers({
            "X-M2M-RI: 45897",
            "Content-Type: application/json"
    })
    @PUT("{path}")
    Call<ResponseHolder> updateAe(
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-Origin") String aeId,
            @Body RequestHolder requestHolder);

    @Headers({
            "X-M2M-RI: 487bgkl"
    })
    @DELETE("{path}")
    Call<Void> deleteAe(
            @Path(value = "path", encoded = true) String path,
            @Header("Authorization") String authorization,
            @Header("X-M2M-Origin") String aeId);
}
