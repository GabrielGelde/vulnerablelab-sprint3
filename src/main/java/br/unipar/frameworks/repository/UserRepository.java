package br.unipar.frameworks.repository;

import br.unipar.frameworks.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // Sprint 03 - Fase 4: busca paginada com Prepared Statement (sem concatenação)
    @Query("select u from User u where lower(u.name) like lower(concat('%', :term, '%'))")
    Page<User> safeSearchByName(@Param("term") String term, Pageable pageable);
}
