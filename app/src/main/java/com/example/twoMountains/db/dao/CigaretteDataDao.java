package com.example.twoMountains.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twoMountains.bean.CigaretteDataBean;

import java.util.List;

@Dao
public interface CigaretteDataDao {
    @Insert(entity = CigaretteDataBean.class)
    void addCigaretteData(CigaretteDataBean cigaretteData);

    @Query("select * from CigaretteDataBean")
    List<CigaretteDataBean> loadAll();

    @Query("select * from CigaretteDataBean where userId = :userId")
    List<CigaretteDataBean> loadAllByUser(int userId);

    @Query("select * from CigaretteDataBean where userId = :userId and date = :date")
    CigaretteDataBean queryCigaretteDataByUserAndDate(int userId, String date);

    @Update
    void updateCigaretteData(CigaretteDataBean cigaretteData);

    @Query("update CigaretteDataBean set motivation = :motivation where userId = :userId and date = :date")
    void updateMotivationByUserAndDate(float motivation,int userId, String date);

    @Query("update CigaretteDataBean set cigaretteQuantity = :cigaretteQuantity where userId = :userId and date = :date")
    void updateCigaretteQuantityByUserAndDate(int cigaretteQuantity,int userId, String date);

    @Query("update CigaretteDataBean set vapeQuantity = :vapeQuantity where userId = :userId and date = :date")
    void updateVapeQuantityByUserAndDate(int vapeQuantity,int userId, String date);

    @Query("delete from CigaretteDataBean where cigaretteId = :cigaretteId")
    void deleteCigaretteDataById(int cigaretteId);
}
