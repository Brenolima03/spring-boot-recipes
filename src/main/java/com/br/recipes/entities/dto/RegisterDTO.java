package com.br.recipes.entities.dto;

import com.br.recipes.entities.Role;

public record RegisterDTO(String login, String password, Role role) {}
