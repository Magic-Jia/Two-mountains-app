package com.example.twoMountains.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.bean.ArticleStarBean;

import java.util.List;

@Dao
public interface ArticleStarDao {
    @Insert(entity = ArticleStarBean.class)
    void addArticleStar(ArticleStarBean articleStar);

    @Query("select * from ArticleStarBean")
    List<ArticleStarBean> loadAll();

    @Query("SELECT ArticleBean.* FROM ArticleBean " +
            "INNER JOIN ArticleStarBean ON ArticleBean.id = ArticleStarBean.articleId " +
            "WHERE ArticleStarBean.userId = :userId")
    List<ArticleBean> loadStarredArticlesByUser(int userId);

    @Query("select * from ArticleStarBean where userId = :userId and articleId = :articleId")
    ArticleStarBean queryArticleStarByUserAndArticle(int userId, int articleId);

    @Update
    void updateArticleStar(ArticleStarBean articleStar);

    @Query("delete from ArticleStarBean where userId = :userId and articleId = :articleId")
    void deleteArticleStarByUserAndArticle(int userId, int articleId);
}
