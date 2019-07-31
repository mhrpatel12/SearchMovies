package com.mihir.searchmovies.rest;

import com.google.gson.JsonElement;
import com.mihir.searchmovies.utils.Urls;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mihir on 26-06-2019.
 */
public interface ApiCallInterface {

    @GET(Urls.searchMovies)
    Observable<JsonElement> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page);

}