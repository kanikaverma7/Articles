package com.dexter.newsapp.data;

import com.dexter.newsapp.data.db.AppRoomDatabase;
import com.dexter.newsapp.data.model.Article;
import com.dexter.newsapp.data.model.ArticleData;
import com.dexter.newsapp.data.model.db.NewsArticleEntity;
import com.dexter.newsapp.data.model.network.NewsArticleModel;
import com.dexter.newsapp.data.restApi.ApiInterface;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;

/**
 * Single source of truth for the app , fetches data from API and Database accordingly
 */
public class Repository {


    private AppRoomDatabase db;
    private ApiInterface apiInterface;

    public Repository(AppRoomDatabase db, ApiInterface apiInterface) {
        this.db = db;
        this.apiInterface = apiInterface;
    }

    /**
     * Fetch data from API
     *
     * @param country  selected country
     * @param category selected news category
     * @param pageNo   page number for pagination
     * @return list of Articles for the specified parameters
     */
    public Single<ArticleData> fetchData(String country, String category, long pageNo) {
        return apiInterface.fetchNews(country, category, "d521212fa7b547c58bea0ad45070de80", pageNo)
                .map(newsResponse -> {

                    List<Article> articleList = new ArrayList<>();
                    for (NewsArticleModel articleNw : newsResponse.getArticleNws()) {
                        Article article = new Article(
                                articleNw.getAuthor(),
                                articleNw.getSource().getName(),
                                articleNw.getTitle(),
                                articleNw.getDescription(),
                                articleNw.getUrlToImage(),
                                articleNw.getUrl(),
                                articleNw.getPublishedAt(),
                                articleNw.getContent()
                        );

                        articleList.add(article);
                    }

                    return new ArticleData(newsResponse.getTotalResults(), articleList);
                });


    }

    /**
     * Download photo from the url
     *
     * @param article
     * @param country
     * @param category
     * @return
     */
    public Single<Article> fetchPhoto(final Article article, final String country, final String category) {
        return Single.create(emitter -> {

            NewsArticleEntity articleDbModel = new NewsArticleEntity(
                    article.getAuthor(),
                    article.getSourceName(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getImageUrl(),
                    article.getUrl(),
                    article.getPublishedAt(),
                    article.getContent(),
                    country,
                    category
            );


            db.newsDao().insertAtricle(articleDbModel);

            emitter.onSuccess(article);
        });
    }


    public Single<ArticleData> getFromDb(String country, String category) {
        return db.newsDao().getAllArticles(country, category).map(new Function<List<NewsArticleEntity>, ArticleData>() {
            @Override
            public ArticleData apply(List<NewsArticleEntity> articleDbModels) throws Exception {

                List<Article> articleList = new ArrayList<>();
                for (NewsArticleEntity a : articleDbModels) {
                    Article article = new Article(
                            a.getAuthor(),
                            a.getSourceName(),
                            a.getTitle(),
                            a.getDescription(),
                            a.getImageBase64(),
                            a.getUrl(),
                            a.getPublishedAt(),
                            a.getContent()
                    );
                    articleList.add(article);
                }
                return new ArticleData(0, articleList);
            }
        });
    }
}
