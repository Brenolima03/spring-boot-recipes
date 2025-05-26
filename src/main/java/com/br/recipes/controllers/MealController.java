package com.br.recipes.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.recipes.entities.Meal;
import com.br.recipes.entities.dto.MealRequestDTO;
import com.br.recipes.entities.dto.MealResponseDTO;
import com.br.recipes.exceptions.MealNotFoundException;
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

  @GetMapping()
  public CollectionModel<EntityModel<MealResponseDTO>> findAll() {
    List<EntityModel<MealResponseDTO>> meals = mealRepository.findAll().stream()
      .map(meal -> {
        MealResponseDTO dto = new MealResponseDTO(meal);
        return EntityModel.of(dto,
          linkTo(methodOn(MealController.class)
            .findOne(meal.getId())).withSelfRel(),
          linkTo(methodOn(MealController.class).findAll()).withRel("meals"));
      })
      .collect(Collectors.toList());

    return CollectionModel.of(
      meals, linkTo(methodOn(MealController.class).findAll()).withSelfRel()
    );
  }

  @GetMapping("/{id}")
  public EntityModel<MealResponseDTO> findOne(@PathVariable UUID id) {
    Meal meal = mealRepository.findById(id)
      .orElseThrow(() -> new MealNotFoundException(id));

    MealResponseDTO dto = new MealResponseDTO(meal);

    return EntityModel.of(dto,
      linkTo(methodOn(MealController.class).findOne(id)).withSelfRel(),
      linkTo(methodOn(MealController.class).findAll()).withRel("meals"));
  }
}
