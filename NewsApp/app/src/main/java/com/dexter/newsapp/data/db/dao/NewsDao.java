package com.dexter.newsapp.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.dexter.newsapp.data.model.db.NewsArticleEntity;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAtricle(NewsArticleEntity articleDbModel);

    @Query("select * from article_table where country =:country and category=:category order by article_id asc")
    Single<List<NewsArticleEntity>> getAllArticles(String country, String category);

}

