package com.dexter.newsapp.data.model.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("totalResults")
    @Expose
    public Long totalResults;
    @SerializedName("articles")
    @Expose
    public List<NewsArticleModel> articleNws = null;

    public NewsResponse(String status, Long totalResults, List<NewsArticleModel> articleNws) {
        this.status = status;
        this.totalResults = totalResults;
        this.articleNws = articleNws;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsArticleModel> getArticleNws() {
        return articleNws;
    }

    public void setArticleNws(List<NewsArticleModel> articleNws) {
        this.articleNws = articleNws;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articleNws=" + articleNws +
                '}';
    }
}
