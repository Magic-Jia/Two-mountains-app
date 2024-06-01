package com.example.twoMountains.db;

import android.content.Context;

import androidx.room.Room;

import com.example.twoMountains.App;
import com.example.twoMountains.db.dao.AdministratorDao;
import com.example.twoMountains.db.dao.ArticleDao;
import com.example.twoMountains.db.dao.ArticleStarDao;
import com.example.twoMountains.db.dao.CigaretteDataDao;
import com.example.twoMountains.db.dao.FMDao;
import com.example.twoMountains.db.dao.QuitPlanDao;
import com.example.twoMountains.db.dao.UserDao;


public class DBCreator {
    private static MyDB db;
    public static void init() {
        if (db == null) {
            db = Room.databaseBuilder(App.getContext(),
                    MyDB.class,
                    "twoMountains_db")
                    .allowMainThreadQueries()
                    .build();
        }
    }

    public static synchronized MyDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                            MyDB.class, "twoMountains_db")
                    .build();
        }
        return db;
    }

    public static UserDao getUserDao() {
        return db.getUserDao();
    }
    public static AdministratorDao getAdministratorDao() {
        return db.getAdministratorDao();
    }
    public static ArticleDao getArticleDao() {
        return db.getArticleDao();
    }
    public static ArticleStarDao getArticleStarDao() {
        return db.getArticleStarDao();
    }
    public static CigaretteDataDao getCigaretteDataDao() {
        return db.getCigaretteDataDao();
    }
    public static QuitPlanDao getQuitPlanDao() {
        return db.getQuitPlanDao();
    }
    public static FMDao getFMDao() {
        return db.getFMDao();
    }
}
