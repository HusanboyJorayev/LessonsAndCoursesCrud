package com.example.lessonscrudproject.repository;


import com.example.lessonscrudproject.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Integer> {

    Optional<Auth>findByUsernameAndEnabledIsTrueAndDeletedAtIsNull(String username);
    Optional<Auth>findByUsernameAndDeletedAtIsNull(String username);

    boolean existsByUsernameAndEnabledIsTrueAndDeletedAtIsNull(String username);
}
