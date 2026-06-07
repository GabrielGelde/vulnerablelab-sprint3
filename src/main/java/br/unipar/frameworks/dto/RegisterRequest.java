package br.unipar.frameworks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Sprint 02 - Fase 2, item 6: Bean Validation aplicada ao request de cadastro.
 */
public record RegisterRequest(
        @NotBlank @Size(min = 2, max = 80) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 100) String password
) {}
