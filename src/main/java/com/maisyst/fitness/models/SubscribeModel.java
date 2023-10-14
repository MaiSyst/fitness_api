package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@Entity(name = "subscribe")
public class SubscribeModel {
    @Id
    @Column(name = "subscribe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscribeId;
    @Column(name = "date_start")
    private Date dateStart;
    @Column(name = "date_end")
    private Date dateEnd;
    @Column(nullable = false)
    private boolean isActive=false;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerModel customer;
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubscriptionModel subscription;
    public SubscribeModel() {}

    public SubscribeModel(Date dateStart, Date dateEnd, boolean isActive, CustomerModel customer, SubscriptionModel subscription) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isActive = isActive;
        this.customer = customer;
        this.subscription = subscription;
    }

    public SubscribeModel(int subscribeId, Date dateStart, Date dateEnd, boolean isActive, CustomerModel customer, SubscriptionModel subscription) {
        this.subscribeId = subscribeId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isActive = isActive;
        this.customer = customer;
        this.subscription = subscription;
    }

    public SubscribeModel(int subscribeId, Date dateStart, Date dateEnd, boolean isActive) {
        this.subscribeId = subscribeId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isActive = isActive;
    }

    public SubscribeModel(int subscribeId, Date dateStart, Date dateEnd, boolean isActive, CustomerModel customer) {
        this.subscribeId = subscribeId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isActive = isActive;
        this.customer = customer;
    }

    public void setSubscribeId(int subscribeId) {
        this.subscribeId = subscribeId;
    }

    public int getSubscribeId() {
        return subscribeId;
    }

    public Date getDateStart() {
        return dateStart;
    }
    public Date getDateEnd() {
        return dateEnd;
    }
    public CustomerModel getCustomer() {
        return customer;
    }
    public SubscriptionModel getSubscription() {
        return subscription;
    }
    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }
    public void setSubscription(SubscriptionModel subscription) {
        this.subscription = subscription;
    }
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
