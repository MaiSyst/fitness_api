package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "customer")
public class CustomerModel {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerId;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "year_of_birth",nullable = false)
    private Date yearOfBirth;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
     @Column(nullable = false)
    private boolean isActive;
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<SubscribeModel> subscribes = new HashSet<>();

    public CustomerModel() {

    }

    public CustomerModel(UUID customerId, String firstName, String lastName, Date yearOfBirth, String address,String username,String password, Set<SubscribeModel> subscribes) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.subscribes = subscribes;
        this.username=username;
        this.password=password;
        this.isActive=true;
    }

    public CustomerModel(UUID customerId, String firstName, String lastName, Date yearOfBirth, String address,String username,String password) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.username=username;
        this.password=password;
        this.isActive=true;
    }

    public CustomerModel(String firstName, String lastName, Date yearOfBirth, String address,String username,String password, Set<SubscribeModel> subscribes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.subscribes = subscribes;
        this.username=username;
        this.password=password;
        this.isActive=true;
    }
public CustomerModel(String firstName, String lastName, Date yearOfBirth, String address,String username,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.username=username;
        this.password=password;
        this.isActive=true;
    }
    public UUID getCustomerId() {
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
