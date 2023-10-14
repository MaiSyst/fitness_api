package com.maisyst.fitness.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "room")
public class RoomModel {
    @Id
    @Column(name = "room_id")
    private String roomId;
    @Column(name = "room_name")
    private String roomName;
    @OneToMany(mappedBy = "room")
    private Set<PlanningModel> plannings=new HashSet<>();
    public RoomModel() {}

    public RoomModel(String roomId, String roomName, Set<PlanningModel> plannings) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.plannings = plannings;
    }
    public RoomModel(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public RoomModel(String roomName, Set<PlanningModel> plannings) {
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

    public Set<PlanningModel> getPlanning() {
        return plannings;
    }

    public void setPlanning(Set<PlanningModel> plannings) {
        this.plannings = plannings;
    }
}
