package com.example.twoMountains.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twoMountains.bean.ArticleBean;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert(entity = ArticleBean.class)
    void addArticle(ArticleBean article);

    @Query("select * from ArticleBean")
    List<ArticleBean> loadAll();

    @Query("select * from ArticleBean where id = :id")
    ArticleBean queryArticleById(int id);

    @Query("update ArticleBean set watchQuantity = :watchQuantity where id = :id")
    void updateWatchQuantityById(int id, int watchQuantity);

    @Update
    void updateArticle(ArticleBean article);

    @Query("delete from ArticleBean where id = :id")
    void deleteArticleById(int id);
}
