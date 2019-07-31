package com.mihir.searchmovies.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.mihir.searchmovies.model.Movie;
import com.mihir.searchmovies.movie_datasource.MoviesDataSourceClass;
import com.mihir.searchmovies.movie_datasource.MoviesDataSourceFactory;
import com.mihir.searchmovies.rest.Repository;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by mihir on 26-06-2019.
 */
public class PagingMovieViewModel extends ViewModel {

    private MoviesDataSourceFactory moviesDataSourceFactory;
    private LiveData<PagedList<Movie>> listLiveData;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PagingMovieViewModel(Repository repository) {
        moviesDataSourceFactory = new MoviesDataSourceFactory(repository, compositeDisposable);
        initializePaging();
    }

    private String query = "";

    private void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();

        moviesDataSourceFactory.query = query;
        listLiveData = new LivePagedListBuilder<>(moviesDataSourceFactory, pagedListConfig)
                .build();

        progressLoadStatus = Transformations.switchMap(moviesDataSourceFactory.getMutableLiveData(), MoviesDataSourceClass::getProgressLiveStatus);

    }

    public void replaceSubscription(LifecycleOwner lifecycleOwner, String query) {
        this.query = query;
        listLiveData.removeObservers(lifecycleOwner);
        initializePaging();
    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<Movie>> getListLiveData() {
        return listLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}