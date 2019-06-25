package com.dexter.newsapp.di.component;

import com.dexter.newsapp.di.module.AppModule;
import com.dexter.newsapp.ui.newsList.NewsListActivity;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void doInjection(NewsListActivity newsListActivity);
}
