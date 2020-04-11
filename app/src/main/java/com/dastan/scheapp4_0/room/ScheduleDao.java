package com.dastan.scheapp4_0.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dastan.scheapp4_0.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {

//    @Query("SELECT * FROM schedule")
//    List<Schedule> getAll();

    @Query("SELECT * FROM schedule")
    LiveData<List<Schedule>> getAll();

    @Insert
    void insert(Schedule schedule);

    @Update
    void update(Schedule schedule);

    @Delete
    void delete(Schedule schedule);
}
