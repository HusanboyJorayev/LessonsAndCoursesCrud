package com.example.lessonscrudproject.repository;



import com.example.lessonscrudproject.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
    Optional<Authorities> findByUsernameAndDeletedAtIsNull(String username);

    @Query(value = "select * from authorities",
            nativeQuery = true
    )
    List<Authorities>findAllWithQuery();
}
