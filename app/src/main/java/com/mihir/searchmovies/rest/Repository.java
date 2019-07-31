package com.mihir.searchmovies.rest;

import com.google.gson.JsonElement;
import com.mihir.searchmovies.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by mihir on 26-06-2019.
 */

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call search movie api
     * */
    public Observable<JsonElement> searchMovies(String query, int index) {
        return apiCallInterface.searchMovies(String.valueOf(Constant.API_KEY), "" + query, index + 1);
    }
}