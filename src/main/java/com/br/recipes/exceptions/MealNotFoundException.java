package com.br.recipes.exceptions;

import java.util.UUID;

public class MealNotFoundException extends RuntimeException {
  public MealNotFoundException(UUID id) {
    super("Could not find meal " + id);
  }
}
