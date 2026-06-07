package br.unipar.frameworks.repository;

import br.unipar.frameworks.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Sprint 03 - Fase 4: paginação nativa do Spring Data JPA
    Page<Comment> findByProductId(Long productId, Pageable pageable);
}
