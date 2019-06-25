package com.dexter.newsapp.data.model;

import java.util.List;

/**
 * ArticleData - contains the list of News articles
 */
public class ArticleData {

    private long totalResults;
    private List<Article> articleList;

    public ArticleData(long totalResults, List<Article> articleList) {
        this.totalResults = totalResults;
        this.articleList = articleList;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
