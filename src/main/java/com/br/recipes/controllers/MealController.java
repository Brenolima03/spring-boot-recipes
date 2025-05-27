package com.br.recipes.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.recipes.entities.Meal;
import com.br.recipes.entities.dto.MealRequestDTO;
import com.br.recipes.exceptions.MealNotFoundException;
import com.br.recipes.repositories.MealRepository;

@RestController
@RequestMapping("/meals")
class MealController {
  private final MealRepository mealRepository;
  private final MealModelAssembler assembler;

  MealController(MealRepository mealRepository, MealModelAssembler assembler) {
    this.mealRepository = mealRepository;
    this.assembler = assembler;
  }

  @PostMapping
  ResponseEntity<?> post(@RequestBody MealRequestDTO mealData) {
    Meal meal = new Meal();
    meal.setName(mealData.name());
    meal.setDescription(mealData.description());
    meal.setImage(mealData.image());

    EntityModel<Meal> model = assembler.toModel(mealRepository.save(meal));

    return ResponseEntity
      .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
      .body(model);
  }

  @GetMapping()
  CollectionModel<EntityModel<Meal>> findAll() {
    List<EntityModel<Meal>> meals = mealRepository.findAll().stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return CollectionModel.of(
      meals, linkTo(methodOn(MealController.class).findAll()).withSelfRel()
    );
  }

  @GetMapping("/{id}")
  EntityModel<Meal> findById(@PathVariable UUID id) {
    Meal meal = mealRepository.findById(id).orElseThrow(() ->
      new MealNotFoundException(id)
    );

    return assembler.toModel(meal);
  }
}
