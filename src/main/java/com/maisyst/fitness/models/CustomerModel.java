package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "customer")
public class CustomerModel {
    @Id
    @Column(name = "customer_id")
    @UuidGenerator
    private String customerId;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "year_of_birth",nullable = false)
    private Date yearOfBirth;
    @Column(nullable = false)
    private String address;
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<SubscribeModel> subscribes = new HashSet<>();

    public CustomerModel() {

    }

    public CustomerModel(String customerId, String firstName, String lastName, Date yearOfBirth, String address, Set<SubscribeModel> subscribes) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.subscribes = subscribes;
    }

    public CustomerModel(String customerId, String firstName, String lastName, Date yearOfBirth, String address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
    }

    public CustomerModel(String firstName, String lastName, Date yearOfBirth, String address, Set<SubscribeModel> subscribes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.subscribes = subscribes;
    }
public CustomerModel(String firstName, String lastName, Date yearOfBirth, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
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

    public Set<SubscribeModel> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(Set<SubscribeModel> subscribes) {
        this.subscribes = subscribes;
    }
}
