package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.MaiDay;

import java.sql.Time;
import java.util.UUID;

public record PlanningResponse(UUID planningId, MaiDay day, Time startTime, Time endTime,String activityLabel,String roomName) {}
