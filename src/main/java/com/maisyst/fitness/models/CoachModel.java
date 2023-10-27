package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;


@Entity(name = "coach")
public class CoachModel {
    @Id
    @Column(name = "coach_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID coachId;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(nullable = false,unique = true)
    private String phone;
    @Column(nullable = false)
    private String address;
     @Column(nullable = false)
    private String speciality;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ActivityModel activityCoach;

    public CoachModel() {
    }

    public CoachModel(String firstName, String lastName, String phone, String address, String speciality, ActivityModel activityCoach) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.speciality = speciality;
        this.activityCoach = activityCoach;
    }

    public CoachModel(UUID coachId, String firstName, String lastName, String phone, String address, String speciality, ActivityModel activityCoach) {
        this.coachId = coachId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.speciality = speciality;
        this.activityCoach = activityCoach;
    }
      public CoachModel(UUID coachId, String firstName, String lastName, String phone, String address, String speciality) {
        this.coachId = coachId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.speciality = speciality;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ActivityModel getActivityCoach() {
        return activityCoach;
    }

    public void setActivityCoach(ActivityModel activityCoach) {
        this.activityCoach = activityCoach;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public UUID getCoachId() {
        return coachId;
    }

    public void setCoachId(UUID coachId) {
        this.coachId = coachId;
    }
}
