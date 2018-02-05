package com.example.matteotognon.assessement;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MatteoTognon on 10/12/2017.
 */

public interface Api {
    @GET("./")
    Call<AppResponse> getInformacao();
}
