package com.dexter.newsapp.di.module;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import com.dexter.newsapp.data.Repository;
import com.dexter.newsapp.data.db.AppRoomDatabase;
import com.dexter.newsapp.data.restApi.ApiClient;
import com.dexter.newsapp.data.restApi.ApiInterface;
import com.dexter.newsapp.utils.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }


    @Provides
    @Singleton
    AppRoomDatabase provideWordRoomDatabase(Context context) {
        return AppRoomDatabase.getDatabase(this.context);
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface() {
        return ApiClient.getClient().create(ApiInterface.class);
    }

    @Provides
    @Singleton
    Repository provideRepository(ApiInterface apiInterface, AppRoomDatabase db) {
        return new Repository(db, apiInterface);
    }


    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Repository myRepository) {
        return new ViewModelFactory(myRepository);
    }

}
