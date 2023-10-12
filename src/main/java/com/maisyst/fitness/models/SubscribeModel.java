package com.maisyst.fitness.models;

import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    private CustomerModel customer;
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private SubscriptionModel subscription;
    public SubscribeModel() {}
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

}
