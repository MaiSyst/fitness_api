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
    @Column
    private Date date;
    @Column
    private Time start_time;
    @Column
    private Time end_time;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private RoomModel room;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityModel activity;
    public PlanningModel() {
    }

    public PlanningModel(Date date, Time start_time, Time end_time, RoomModel room, ActivityModel activity) {
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.room = room;
        this.activity = activity;
    }

    public PlanningModel(int planningId, Date date, Time start_time, Time end_time, RoomModel room, ActivityModel activity) {
        this.planningId = planningId;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.room = room;
        this.activity = activity;
    }

    public PlanningModel(int planningId, Date date, Time start_time, Time end_time, RoomModel room) {
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.room = room;
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

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
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
