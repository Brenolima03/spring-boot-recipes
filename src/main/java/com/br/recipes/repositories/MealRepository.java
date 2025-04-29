package com.br.recipes.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.recipes.entities.Meal;

public interface MealRepository extends JpaRepository<Meal, UUID> {}
