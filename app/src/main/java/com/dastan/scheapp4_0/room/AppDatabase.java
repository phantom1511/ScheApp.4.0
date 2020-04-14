package com.dastan.scheapp4_0.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dastan.scheapp4_0.Group;
import com.dastan.scheapp4_0.Schedule;

@Database(entities = {Schedule.class, Group.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ScheduleDao scheduleDao();
    public abstract GroupDao groupDao();

}
