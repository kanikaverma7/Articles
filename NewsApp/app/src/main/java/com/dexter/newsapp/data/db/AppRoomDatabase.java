package com.dexter.newsapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.dexter.newsapp.data.db.dao.NewsDao;
import com.dexter.newsapp.data.model.db.NewsArticleEntity;

/* Database to hold the news table for offline usage*/
@Database(entities = {NewsArticleEntity.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

    private static AppRoomDatabase INSTANCE;

    /** Get database singleton instance throughout the app
     * @param context context
     * @return instance of App class
     */
    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
