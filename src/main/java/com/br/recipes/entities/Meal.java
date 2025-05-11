package com.br.recipes.entities;

import java.util.UUID;

import com.br.recipes.entities.dto.MealRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="meals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Meal {
  @Id
  @GeneratedValue(strategy=GenerationType.UUID)
  private UUID id;
  private String name;
  @Column(columnDefinition = "TEXT")
  private String description;
  @Column(columnDefinition = "TEXT")
  private String image;

  public Meal(MealRequestDTO mealData) {
    this.name = mealData.name();
    this.description = mealData.description();
    this.image = mealData.image();
  }
}
