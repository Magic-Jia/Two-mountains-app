package com.example.twoMountains.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.twoMountains.bean.AdministratorBean;
import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.bean.ArticleStarBean;
import com.example.twoMountains.bean.CigaretteDataBean;
import com.example.twoMountains.bean.FMBean;
import com.example.twoMountains.bean.QuitPlanBean;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.converter.StringTypeConverter;
import com.example.twoMountains.db.dao.AdministratorDao;
import com.example.twoMountains.db.dao.ArticleDao;
import com.example.twoMountains.db.dao.ArticleStarDao;
import com.example.twoMountains.db.dao.CigaretteDataDao;
import com.example.twoMountains.db.dao.FMDao;
import com.example.twoMountains.db.dao.QuitPlanDao;
import com.example.twoMountains.db.dao.UserDao;


@Database(entities = {
        UserBean.class,
        AdministratorBean.class,
        ArticleBean.class,
        ArticleStarBean.class,
        CigaretteDataBean.class,
        QuitPlanBean.class,
        FMBean.class
}, version = 1, exportSchema = false)
@TypeConverters({StringTypeConverter.class})
public abstract class MyDB extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract AdministratorDao getAdministratorDao();
    public abstract ArticleDao getArticleDao();
    public abstract ArticleStarDao getArticleStarDao();
    public abstract CigaretteDataDao getCigaretteDataDao();
    public abstract QuitPlanDao getQuitPlanDao();
    public abstract FMDao getFMDao();
}
