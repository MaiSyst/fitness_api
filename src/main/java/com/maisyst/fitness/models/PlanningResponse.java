package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.MaiDay;

import java.sql.Time;

public record PlanningResponse(String planningId, MaiDay day, Time startTime, Time endTime,String activityLabel,String roomName) {}
