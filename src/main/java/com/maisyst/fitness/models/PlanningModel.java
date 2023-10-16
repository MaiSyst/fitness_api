package com.maisyst.fitness.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity(name = "planning")
public class PlanningModel {
    @Id
    @Column(name = "planning_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planningId;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false,name = "start_time")
    private Time startTime;
    @Column(nullable = false,name = "end_time")
    private Time endTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private RoomModel room;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityModel activity;
    public PlanningModel() {
    }

    public PlanningModel(Date date, Time startTime, Time endTime, RoomModel room, ActivityModel activity) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.activity = activity;
    }

    public PlanningModel(int planningId, Date date, Time startTime, Time endTime, RoomModel room, ActivityModel activity) {
        this.planningId = planningId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.activity = activity;
    }

    public PlanningModel(int planningId, Date date, Time startTime, Time endTime, RoomModel room) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.planningId = planningId;
    }
     public PlanningModel(int planningId, Date date, Time startTime, Time endTime, ActivityModel activity) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
        this.planningId = planningId;
    }

    public int getPlanningId() {
        return planningId;
    }

    public void setPlanningId(int planningId) {
        this.planningId = planningId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time start_time) {
        this.startTime = start_time;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time end_time) {
        this.endTime = end_time;
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
