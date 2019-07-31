package com.mihir.searchmovies.rest;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mihir.searchmovies.viewmodel.PagingMovieViewModel;

import javax.inject.Inject;

/**
 * Created by mihir on 26-06-2019.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PagingMovieViewModel.class)) {
            return (T) new PagingMovieViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
