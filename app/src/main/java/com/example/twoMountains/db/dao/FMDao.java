package com.example.twoMountains.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twoMountains.bean.FMBean;

import java.util.List;

@Dao
public interface FMDao {
    @Insert(entity = FMBean.class)
    public void insert(FMBean fmBean);

    @Query("select * from fmbean where upId=:upId")
    List<FMBean> queryByUpId(int upId);
    @Query("select * from fmbean where type=:type")
    List<FMBean> queryByType(int type);

    @Query("select * from fmbean")
    List<FMBean> queryAll();

    @Query("delete from fmbean where fmId =:id")
    void deleteFMByFMId(int id);
}
