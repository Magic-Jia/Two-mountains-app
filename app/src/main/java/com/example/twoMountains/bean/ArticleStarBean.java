package com.example.twoMountains.bean;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(foreignKeys = {
        @ForeignKey(entity = ArticleBean.class,
                parentColumns = "id",
                childColumns = "articleId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = UserBean.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE)
    },
        primaryKeys = {"articleId","userId"},
        indices = {@Index("userId")}
)
public class ArticleStarBean {
    public int articleId;
    public int userId;
}
