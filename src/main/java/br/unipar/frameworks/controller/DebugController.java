package br.unipar.frameworks.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Sprint 02 - Fase 1, item 8: endpoint de debug restrito a ADMIN; rota H2 removida da resposta.
 */
@RestController
@RequestMapping("/api/debug")
@PreAuthorize("hasRole('ADMIN')")
public class DebugController {

    @GetMapping("/config")
    public Map<String, String> config() {
        return Map.of(
                "database", "H2 em memória",
                "profile", "lab",
                "warning", "Acesso restrito"
        );
    }

    @GetMapping("/error-example")
    public String errorExample() {
        throw new RuntimeException("Erro interno simulado: falha ao consultar tabela");
    }
}
