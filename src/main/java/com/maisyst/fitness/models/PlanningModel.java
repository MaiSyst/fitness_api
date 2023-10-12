package com.maisyst.fitness.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity(name = "planning")
public class PlanningModel {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Date date;
    @Column
    private Date hourStart;
    @Column
    private Date hourEnd;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private RoomModel room;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityModel activity;
    public PlanningModel() {
    }
    public PlanningModel(Date date, Date hourStart, Date hourEnd, RoomModel room, ActivityModel activity) {
        this.date = date;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.room = room;
        this.activity = activity;
    }
    public PlanningModel(int id,Date date, Date hourStart, Date hourEnd, RoomModel room, ActivityModel activity) {
        this.date = date;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.room = room;
        this.id = id;
        this.activity = activity;
    }
    public long getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getHourStart() {
        return hourStart;
    }
    public void setHourStart(Date hourStart) {
        this.hourStart = hourStart;
    }
    public Date getHourEnd() {
        return hourEnd;
    }
    public void setHourEnd(Date hourEnd) {
        this.hourEnd = hourEnd;
    }
    public RoomModel getRoom() {
        return room;
    }
    public void setRoom(RoomModel room) {
        this.room = room;
    }
    public ActivityModel getActivity() {
        return activity;
    }
    public void setActivity(ActivityModel activity) {
        this.activity = activity;
    }
}
