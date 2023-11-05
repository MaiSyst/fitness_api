package com.maisyst.fitness.models;

import java.util.List;

public record DeleteManyRequest<T>(List<T> ids) {
}
