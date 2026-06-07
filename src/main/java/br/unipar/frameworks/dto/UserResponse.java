package br.unipar.frameworks.dto;

import br.unipar.frameworks.model.User;

/**
 * Sprint 02 - Fase 1, item 1: DTO de resposta sem expor senha nem entidade JPA.
 */
public record UserResponse(Long id, String name, String email, String role) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
