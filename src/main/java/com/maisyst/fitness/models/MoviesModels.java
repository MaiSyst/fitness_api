package com.maisyst.fitness.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "movies")
public class MoviesModels {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String videoName;
    @Column
    private String duration;
    @Column
    private String fitnessType;

    public MoviesModels(UUID id, String videoName, String duration, String fitnessType) {
        this.id = id;
        this.videoName = videoName;
        this.duration = duration;
        this.fitnessType = fitnessType;
    }

    public MoviesModels(String videoName, String duration, String fitnessType) {
        this.videoName = videoName;
        this.duration = duration;
        this.fitnessType = fitnessType;
    }
    public MoviesModels(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFitnessType() {
        return fitnessType;
    }

    public void setFitnessType(String fitnessType) {
        this.fitnessType = fitnessType;
    }
}
