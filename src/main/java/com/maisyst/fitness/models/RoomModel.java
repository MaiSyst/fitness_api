package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Entity(name = "room")
public class RoomModel {
    @Id
    @Column(name = "room_id")
    @UuidGenerator
    private String roomId;
    @Column(name = "room_name",nullable = false,unique = true)
    private String roomName;
    @OneToMany(mappedBy = "room")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PlanningModel> plannings=new ArrayList<>();
    public RoomModel() {}

    public RoomModel(String roomId, String roomName, List<PlanningModel> plannings) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.plannings = plannings;
    }
    public RoomModel(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public RoomModel(String roomName, List<PlanningModel> plannings) {
        this.roomName = roomName;
        this.plannings = plannings;
    }

    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<PlanningModel> getPlanning() {
        return plannings;
    }

    public void setPlanning(List<PlanningModel> plannings) {
        this.plannings = plannings;
    }
}
