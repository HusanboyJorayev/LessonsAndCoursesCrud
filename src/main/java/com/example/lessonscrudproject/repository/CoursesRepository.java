package com.example.lessonscrudproject.repository;

import com.example.lessonscrudproject.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursesRepository extends JpaRepository<Courses,Integer> {

    Optional<Courses>findByIdAndDeletedAtIsNull(Integer id);
}
