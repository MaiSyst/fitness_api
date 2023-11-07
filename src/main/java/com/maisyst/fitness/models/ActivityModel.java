package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.UUID;

@Entity(name = "activity")
public class ActivityModel {
    @Id
    @Column(name = "activity_id")
    @UuidGenerator
    private String activityId;
    @Column(nullable = false, unique = true)
    private String label;
    @Column(nullable = false)
    private String description;
    @OneToMany(mappedBy = "activityCoach", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CoachModel> coach = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "concern",
            joinColumns = {
                    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id")
            }
    )
    @JsonIgnore
    private List<SubscriptionModel> subscriptions=new ArrayList<>();
    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private List<PlanningModel> plannings=new ArrayList<>();

    public ActivityModel() {
    }


    public ActivityModel(String activityId, String label, String description, List<CoachModel> coach, List<SubscriptionModel> subscriptions, List<PlanningModel> plannings) {
        this.activityId = activityId;
        this.label = label;
        this.description = description;
        this.coach = coach;
        this.subscriptions = subscriptions;
        this.plannings = plannings;
    }

    public ActivityModel(String label, String description, List<CoachModel> coach, List<SubscriptionModel> subscriptions, List<PlanningModel> plannings) {
        this.label = label;
        this.description = description;
        this.coach = coach;
        this.subscriptions = subscriptions;
        this.plannings = plannings;
    }

    public ActivityModel(String activityId, String label, String description, List<CoachModel> coach, List<SubscriptionModel> subscriptions) {
        this.activityId = activityId;
        this.label = label;
        this.description = description;
        this.coach = coach;
        this.subscriptions = subscriptions;
    }

    public ActivityModel(String activityId, String label, String description) {
        this.activityId = activityId;
        this.label = label;
        this.description = description;
    }
    public ActivityModel(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CoachModel> getCoach() {
        return coach;
    }

    public void setCoach(List<CoachModel> coach) {
        this.coach = coach;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public List<SubscriptionModel> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionModel> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<PlanningModel> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<PlanningModel> plannings) {
        this.plannings = plannings;
    }
}
