package com.br.recipes.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.br.recipes.entities.Meal;

@Component
class MealModelAssembler
implements RepresentationModelAssembler<Meal, EntityModel<Meal>> {
  @Override
  public EntityModel<Meal> toModel(Meal meal) {
    return EntityModel.of(meal,
      linkTo(methodOn(MealController.class)
        .findById(meal.getId())).withSelfRel(),
      linkTo(methodOn(MealController.class).findAll()).withRel("meals"));
  }
}
