package br.unipar.frameworks.dto;

import br.unipar.frameworks.model.Comment;

/**
 * Sprint 02 - Fase 1, item 1: DTO de resposta de comentário sem expor entidade JPA diretamente.
 */
public record CommentResponse(Long id, String text, Long productId) {

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getText(), comment.getProduct().getId());
    }
}
