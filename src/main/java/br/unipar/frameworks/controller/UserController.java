package br.unipar.frameworks.controller;

import br.unipar.frameworks.dto.UserResponse;
import br.unipar.frameworks.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Sprint 03 - Fase 4: listagem e busca paginadas (Page<UserResponse>);
 * parâmetros ?page=0&size=10&sort=name via Pageable injetado pelo Spring MVC.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Page<UserResponse> listUsers(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::from);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    @GetMapping("/search")
    public Page<UserResponse> search(@RequestParam String term,
                                     @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return userRepository.safeSearchByName(term, pageable).map(UserResponse::from);
    }
}
