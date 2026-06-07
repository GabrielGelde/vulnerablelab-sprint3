package br.unipar.frameworks.dto;

/**
 * Sprint 02 - Fase 2, item 4: resposta de login com JWT real, sem expor entidade User.
 */
public record LoginResponse(String token, String tokenType, long expiresInMs, UserResponse user) {

    public static LoginResponse of(String token, long expiresInMs, UserResponse user) {
        return new LoginResponse(token, "Bearer", expiresInMs, user);
    }
}
