package com.dexter.newsapp.utils;

import com.dexter.newsapp.data.model.ArticleData;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.dexter.newsapp.utils.Status.ERROR;
import static com.dexter.newsapp.utils.Status.SUCCESS;

public class ApiResponse {

    public final Status status;

    @Nullable
    public final ArticleData data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable ArticleData data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(Status.LOADING, null, null);
    }

    public static ApiResponse success(@NonNull ArticleData data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }

}