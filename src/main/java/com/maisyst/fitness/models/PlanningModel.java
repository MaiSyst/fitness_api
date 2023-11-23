package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.MaiDay;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Time;

@Entity(name = "planning")
public class PlanningModel {
    @Id
    @Column(name = "planning_id")
    @UuidGenerator
    private String planningId;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MaiDay day;
    @Column(nullable = false,name = "start_time")
    private Time startTime;
    @Column(nullable = false,name = "end_time")
    private Time endTime;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoomModel room;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "activity_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ActivityModel activity;
    public PlanningModel() {
    }

    public PlanningModel(MaiDay day, Time startTime, Time endTime, RoomModel room, ActivityModel activity) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.activity = activity;
    }

    public PlanningModel(String planningId, MaiDay day, Time startTime, Time endTime, RoomModel room, ActivityModel activity) {
        this.planningId = planningId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.activity = activity;
    }

    public PlanningModel(String planningId, MaiDay day, Time startTime, Time endTime, RoomModel room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.planningId = planningId;
    }
     public PlanningModel(String planningId, MaiDay day, Time startTime, Time endTime, ActivityModel activity) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
        this.planningId = planningId;
    }

    public String getPlanningId() {
        return planningId;
    }

    public void setPlanningId(String planningId) {
        this.planningId = planningId;
    }

    public MaiDay getDay() {
        return day;
    }

    public void setDay(MaiDay day) {
        this.day = day;
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
