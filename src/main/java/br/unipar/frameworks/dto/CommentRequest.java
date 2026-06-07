package br.unipar.frameworks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Sprint 02 - Fase 2, item 6: Bean Validation aplicada ao request de comentário.
 */
public record CommentRequest(
        @NotBlank @Size(max = 2000) String text,
        @NotNull Long productId
) {}
