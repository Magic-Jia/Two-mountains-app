package com.example.twoMountains.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twoMountains.bean.QuitPlanBean;

import java.util.List;

@Dao
public interface QuitPlanDao {
    @Insert(entity = QuitPlanBean.class)
    void addQuitPlan(QuitPlanBean quitPlan);

    @Query("select * from QuitPlanBean")
    List<QuitPlanBean> loadAll();

    @Query("select * from QuitPlanBean where userId = :userId")
    QuitPlanBean queryQuitPlanByUser(int userId);

    @Update
    void updateQuitPlan(QuitPlanBean quitPlan);

    @Query("UPDATE QuitPlanBean SET quitDate = :newQuitDate WHERE userId = :userId")
    void updateQuitDateByUser(int userId, String newQuitDate);
}
