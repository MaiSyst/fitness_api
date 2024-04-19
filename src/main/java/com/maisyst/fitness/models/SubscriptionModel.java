package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.TypeSubscription;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "subscription")
public class SubscriptionModel {
    @Id()
    @Column(name = "subscription_id")
    @UuidGenerator
    private String subscriptionId;
    @Column(nullable = false)
    private String label;
    @Column(nullable = false)
    private int price;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,unique = true)
    private TypeSubscription type=TypeSubscription.GOLD;
    @ManyToMany(mappedBy = "subscriptions",fetch = FetchType.LAZY)
    private List<ActivityModel> activities=new ArrayList<>();
    @OneToMany(mappedBy = "subscription")
    private List<SubscribeModel> subscribes=new ArrayList<>();
    public SubscriptionModel() {}

    public SubscriptionModel(String subscriptionId, String label, int price, TypeSubscription type, List<ActivityModel> activities, List<SubscribeModel> subscribes) {
        this.subscriptionId = subscriptionId;
        this.label = label;
        this.price = price;
        this.type = type;
        this.activities = activities;
        this.subscribes = subscribes;
    }

    public SubscriptionModel(String subscriptionId, String label, int price, TypeSubscription type, List<ActivityModel> activities) {
        this.subscriptionId = subscriptionId;
        this.label = label;
        this.price = price;
        this.type = type;
        this.activities = activities;
    }

    public SubscriptionModel(String subscriptionId, String label, int price, TypeSubscription type) {
        this.subscriptionId = subscriptionId;
        this.label = label;
        this.price = price;
        this.type = type;
    }
     public SubscriptionModel(String label, int price, TypeSubscription type) {
        this.label = label;
        this.price = price;
        this.type = type;
    }
    public SubscriptionModel(TypeSubscription type) {
        this.type = type;
    }
    public SubscriptionModel(String label, int price, TypeSubscription type, List<ActivityModel> activities, List<SubscribeModel> subscribes) {
        this.label = label;
        this.price = price;
        this.type = type;
        this.activities = activities;
        this.subscribes = subscribes;
    }
    public String getSubscriptionId() {
        return subscriptionId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ActivityModel> getActivities() {
        return activities;
    }

    public List<SubscribeModel> getSubscribes() {
        return subscribes;
    }

    public void setActivities(List<ActivityModel> activities) {
        this.activities = activities;
    }

    public void setSubscribes(List<SubscribeModel> subscribes) {
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
