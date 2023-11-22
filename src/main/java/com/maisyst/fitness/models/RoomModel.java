package com.maisyst.fitness.models;

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
    @Column(name = "room_name", nullable = false, unique = true)
    private String roomName;
    @OneToMany(mappedBy = "room")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<PlanningModel> plannings = new ArrayList<>();
    @OneToMany(mappedBy = "room")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CustomerModel> customers = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "manager_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserModel manager;

    public RoomModel() {
    }

    public RoomModel(String roomId, String roomName, UserModel manager,
                     List<CustomerModel> customers, List<PlanningModel> plannings) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.plannings = plannings;
        this.manager = manager;
        this.customers = customers;
    }

    public RoomModel(String roomId, String roomName, UserModel manager) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.manager = manager;
    }

    public RoomModel(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
    public RoomModel(String roomName,UserModel manager) {
        this.manager = manager;
        this.roomName = roomName;
    }

    public RoomModel(String roomId, String roomName, UserModel manager, List<CustomerModel> customers) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.manager = manager;
        this.customers = customers;
    }

    public RoomModel(String roomName, UserModel manager, List<CustomerModel> customers, List<PlanningModel> plannings) {
        this.roomName = roomName;
        this.plannings = plannings;
        this.manager = manager;
        this.customers = customers;
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

    public UserModel getManager() {
        return manager;
    }

    public void setManager(UserModel manager) {
        this.manager = manager;
    }

    public List<CustomerModel> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerModel> customers) {
        this.customers = customers;
    }


    public List<PlanningModel> getPlanning() {
        return plannings;
    }

    public void setPlanning(List<PlanningModel> plannings) {
        this.plannings = plannings;
    }
}
