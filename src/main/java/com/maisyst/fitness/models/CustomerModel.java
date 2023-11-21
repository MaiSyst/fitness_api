package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.util.*;

@Entity(name = "customer")
public class CustomerModel {
    @Id
    @Column(name = "customer_id")
    @UuidGenerator
    private String customerId;
    @Column(name = "identity_emf", unique = true)
    private String identityEMF;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "year_of_birth", nullable = false)
    private Date yearOfBirth;
    @Column(nullable = false)
    private String address;
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<SubscribeModel> subscribes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    RoomModel room;

    public CustomerModel() {

    }

    public CustomerModel(String customerId, String firstName, String lastName, Date yearOfBirth, String address, String identityEMF, RoomModel room, List<SubscribeModel> subscribes) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.subscribes = subscribes;
        this.identityEMF = identityEMF;
        this.room = room;
    }

    public CustomerModel(String customerId, String firstName,
                         String lastName,
                         Date yearOfBirth, String address,
                         String identityEMF, RoomModel room) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.identityEMF = identityEMF;
        this.room = room;
    }

    public CustomerModel(String firstName, String lastName, Date yearOfBirth, String address, String identityEMF, RoomModel room, List<SubscribeModel> subscribes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.subscribes = subscribes;
        this.identityEMF = identityEMF;
        this.room = room;
    }

    public CustomerModel(String firstName, String lastName, Date yearOfBirth, String address, String identityEMF, RoomModel room) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.identityEMF = identityEMF;
        this.room = room;
    }

    public CustomerModel(String customerId, String firstName, String lastName, Date yearOfBirth, String address, String identityEMF) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.identityEMF = identityEMF;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public Date getYearOfBirth() {
        return yearOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYearOfBirth(Date yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<SubscribeModel> getSubscribes() {
        return subscribes;
    }

    public void setIdentityEMF(String identityEMF) {
        this.identityEMF = identityEMF;
    }

    public String getIdentityEMF() {
        return identityEMF;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public void setSubscribes(List<SubscribeModel> subscribes) {
        this.subscribes = subscribes;
    }
}
