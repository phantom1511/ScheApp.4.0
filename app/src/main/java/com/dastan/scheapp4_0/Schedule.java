package com.dastan.scheapp4_0;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Schedule implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String time;
    private String lesson;
    private String type;
    private String room;
    private String group;
    private String days;

    public Schedule(String time, String lesson, String type, String room, String group, String days) {
        this.time = time;
        this.lesson = lesson;
        this.type = type;
        this.room = room;
        this.group = group;
        this.days = days;
    }

    public Schedule() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
