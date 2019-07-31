package com.mihir.searchmovies.di;

import com.mihir.searchmovies.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MainActivity mainActivity);

}
