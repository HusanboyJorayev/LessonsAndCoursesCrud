package com.example.lessonscrudproject.repository;

import com.example.lessonscrudproject.model.Courses;
import com.example.lessonscrudproject.model.Lessons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonsRepository extends JpaRepository<Lessons,Integer> {
    Optional<Lessons> findByIdAndDeletedAtIsNull(Integer id);

    Page<Lessons>findAllByDeletedAtIsNull(Pageable pageable);
}
