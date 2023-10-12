package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.TypeSubscription;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "subscription")
public class SubscriptionModel {
    @Id()
    @Column(name = "subscription_id",unique = true)
    private String subscriptionId;
    @Column(nullable = false)
    private String label;
    @Column(nullable = false)
    private double price;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TypeSubscription type=TypeSubscription.GOLD;
    @ManyToMany(mappedBy = "subscriptions",fetch = FetchType.LAZY)
    private Set<ActivityModel> activities=new HashSet<>();
    @OneToMany(mappedBy = "subscription")
    private Set<SubscribeModel> subscribes=new HashSet<>();
    public SubscriptionModel() {}

    public SubscriptionModel(String subscriptionId, String label, double price, TypeSubscription type, Set<ActivityModel> activities, Set<SubscribeModel> subscribes) {
        this.subscriptionId = subscriptionId;
        this.label = label;
        this.price = price;
        this.type = type;
        this.activities = activities;
        this.subscribes = subscribes;
    }

    public SubscriptionModel(String label, double price, TypeSubscription type, Set<ActivityModel> activities, Set<SubscribeModel> subscribes) {
        this.label = label;
        this.price = price;
        this.type = type;
        this.activities = activities;
        this.subscribes = subscribes;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<ActivityModel> getActivities() {
        return activities;
    }

    public Set<SubscribeModel> getSubscribes() {
        return subscribes;
    }

    public void setActivities(Set<ActivityModel> activities) {
        this.activities = activities;
    }

    public void setSubscribes(Set<SubscribeModel> subscribes) {
        this.subscribes = subscribes;
    }

    public TypeSubscription getType() {
        return type;
    }

    public void setType(TypeSubscription type) {
        this.type = type;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
