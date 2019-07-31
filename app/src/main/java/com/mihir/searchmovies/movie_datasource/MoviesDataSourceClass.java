package com.mihir.searchmovies.movie_datasource;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mihir.searchmovies.model.Movie;
import com.mihir.searchmovies.model.MoviesResponse;
import com.mihir.searchmovies.utils.Constant;
import com.mihir.searchmovies.rest.Repository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by mihir on 26-06-2019.
 */
public class MoviesDataSourceClass extends PageKeyedDataSource<Integer, Movie> {

    private Repository repository;
    private int sourceIndex;
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;
    private String query;

    MoviesDataSourceClass(Repository repository, String query, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.query = query;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
    }


    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {

        repository.searchMovies(query, sourceIndex)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            MoviesResponse moviesResponse = new Gson().fromJson(result, MoviesResponse.class);
                            if (!moviesResponse.getMovies().isEmpty()) {
                                sourceIndex++;
                                callback.onResult(moviesResponse.getMovies(), null, sourceIndex);
                            }
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)

                );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

        repository.searchMovies(query, params.key)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe((JsonElement result) -> {
                            progressLiveStatus.postValue(Constant.LOADED);

                            MoviesResponse moviesResponse = new Gson().fromJson(result, MoviesResponse.class);
                            if (!moviesResponse.getMovies().isEmpty()) {
                                callback.onResult(moviesResponse.getMovies(), moviesResponse.getPage() > moviesResponse.getTotalPages() ? null : params.key + 1);
                            }
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)
                );
    }
}
