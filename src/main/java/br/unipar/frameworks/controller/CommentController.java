package br.unipar.frameworks.controller;

import br.unipar.frameworks.dto.CommentRequest;
import br.unipar.frameworks.dto.CommentResponse;
import br.unipar.frameworks.model.Comment;
import br.unipar.frameworks.model.Product;
import br.unipar.frameworks.repository.CommentRepository;
import br.unipar.frameworks.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.HtmlUtils;

/**
 * Sprint 03 - Fase 4: listagem de comentários paginada por produto.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public CommentController(CommentRepository commentRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{productId}")
    public Page<CommentResponse> listByProduct(@PathVariable Long productId,
                                               @PageableDefault(size = 10) Pageable pageable) {
        return commentRepository.findByProductId(productId, pageable).map(CommentResponse::from);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@Valid @RequestBody CommentRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        Comment comment = new Comment();
        comment.setText(HtmlUtils.htmlEscape(request.text()));
        comment.setProduct(product);
        return CommentResponse.from(commentRepository.save(comment));
    }
}
