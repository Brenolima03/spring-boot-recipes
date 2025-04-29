package com.br.recipes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.recipes.entities.Meal;
import com.br.recipes.entities.dto.MealRequestDTO;
import com.br.recipes.entities.dto.MealResponseDTO;
import com.br.recipes.repositories.MealRepository;

@RestController
@RequestMapping("/meals")
public class MealController {
  @Autowired
  private MealRepository mealRepository;

  @PostMapping
  public void post(@RequestBody MealRequestDTO mealData) {
    Meal meal = new Meal(mealData);
    mealRepository.save(meal);
  }

  @GetMapping
  public List<MealResponseDTO> getAll() {
    List<MealResponseDTO> mealList =
      mealRepository.findAll().stream().map(MealResponseDTO::new).toList();
    return mealList;
  }
}
