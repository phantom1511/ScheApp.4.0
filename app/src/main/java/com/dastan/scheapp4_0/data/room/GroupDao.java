package com.dastan.scheapp4_0.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dastan.scheapp4_0.Group;
import com.dastan.scheapp4_0.Schedule;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM `group`")
    LiveData<List<Group>> getAll();

    @Insert
    void insert(Group group);

    @Update
    void update(Group group);

    @Delete
    void delete(Group group);
}
