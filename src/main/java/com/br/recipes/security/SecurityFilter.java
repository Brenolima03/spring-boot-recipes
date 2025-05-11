package com.br.recipes.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication
.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.br.recipes.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  @Autowired
  TokenService tokenService;

  @Autowired
  UserRepository userRepository;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if (token != null) {
      try {
        // Validate token and extract login and role
        String[] tokenData = tokenService.validateTokenWithRole(token);
        String login = tokenData[0];
        String roleFromToken = tokenData[1];

        // Fetch user from the database
        UserDetails user = userRepository.findByLogin(login);

        if (user != null) {
          // Check if the role from the token matches the user's authorities
          boolean hasMatchingRole = user.getAuthorities().stream()
            .anyMatch(auth ->
              auth.getAuthority().equals("ROLE_" + roleFromToken) ||
              auth.getAuthority().equals(roleFromToken)
            );

          if (!hasMatchingRole) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Role mismatch error.");
            return;
          }

          // Set authentication context
          var authentication = new UsernamePasswordAuthenticationToken(
            user, null, user.getAuthorities()
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (IOException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token validation error.");
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
