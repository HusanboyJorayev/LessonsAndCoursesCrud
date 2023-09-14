package com.example.lessonscrudproject.repository;



import com.example.lessonscrudproject.model.AuthAccessSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthAccessSessionRepository extends CrudRepository<AuthAccessSession,String> {
}
