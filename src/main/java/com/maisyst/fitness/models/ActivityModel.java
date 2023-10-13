package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "activity")
public class ActivityModel {
    @Id
    @Column(name = "activity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityId;
    @Column(nullable = false, unique = true)
    private String label;
    @Column(nullable = false)
    private String description;
    @OneToMany(mappedBy = "activityCoach", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CoachModel> coach = new HashSet<>();
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
    private Set<SubscriptionModel> subscriptions=new HashSet<>();
    @OneToMany(mappedBy = "activity")
    private Set<PlanningModel> plannings=new HashSet<>();

    public ActivityModel() {
    }


    public ActivityModel(int activityId, String label, String description, Set<CoachModel> coach, Set<SubscriptionModel> subscriptions, Set<PlanningModel> plannings) {
        this.activityId = activityId;
        this.label = label;
        this.description = description;
        this.coach = coach;
        this.subscriptions = subscriptions;
        this.plannings = plannings;
    }

    public ActivityModel(String label, String description, Set<CoachModel> coach, Set<SubscriptionModel> subscriptions, Set<PlanningModel> plannings) {
        this.label = label;
        this.description = description;
        this.coach = coach;
        this.subscriptions = subscriptions;
        this.plannings = plannings;
    }

    public int getActivityId() {
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

    public Set<CoachModel> getCoach() {
        return coach;
    }

    public void setCoach(Set<CoachModel> coach) {
        this.coach = coach;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Set<SubscriptionModel> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<SubscriptionModel> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<PlanningModel> getPlannings() {
        return plannings;
    }

    public void setPlannings(Set<PlanningModel> plannings) {
        this.plannings = plannings;
    }
}
