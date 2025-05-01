package com.br.recipes.entities;

import java.util.UUID;

import com.br.recipes.entities.dto.MealRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="meals")
@Getter @Setter
public class Meal {
  @Id
  @GeneratedValue(strategy=GenerationType.UUID)
  private UUID id;
  private String name;
  @Column(columnDefinition = "TEXT")
  private String image;
  private Float price;

  public Meal() {}
  public Meal(UUID id, String name, String image, Float price) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.price = price;
  }
  public Meal(MealRequestDTO mealData) {
    this.name = mealData.name();
    this.image = mealData.image();
    this.price = mealData.price();
  }
}
