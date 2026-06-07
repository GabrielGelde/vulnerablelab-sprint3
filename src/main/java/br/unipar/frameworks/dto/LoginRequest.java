package br.unipar.frameworks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Sprint 02 - Fase 2, item 6: Bean Validation aplicada ao request de login.
 */
public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 100) String password
) {}
