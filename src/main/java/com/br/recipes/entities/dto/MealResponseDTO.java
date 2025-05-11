package com.br.recipes.entities.dto;

import java.util.UUID;

import com.br.recipes.entities.Meal;

public record MealResponseDTO(
  UUID id, String name, String description, String image
) {
  public MealResponseDTO(Meal meal) {
    this(meal.getId(), meal.getName(), meal.getDescription(), meal.getImage());
  }
}
