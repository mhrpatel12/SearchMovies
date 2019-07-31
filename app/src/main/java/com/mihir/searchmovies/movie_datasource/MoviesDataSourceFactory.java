package com.mihir.searchmovies.movie_datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.mihir.searchmovies.model.Movie;
import com.mihir.searchmovies.rest.Repository;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by mihir on 26-06-2019.
 */
public class MoviesDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private MutableLiveData<MoviesDataSourceClass> liveData;
    private Repository repository;
    private CompositeDisposable compositeDisposable;
    public String query;

    public MoviesDataSourceFactory(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<MoviesDataSourceClass> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        MoviesDataSourceClass dataSourceClass = new MoviesDataSourceClass(repository, query, compositeDisposable);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}