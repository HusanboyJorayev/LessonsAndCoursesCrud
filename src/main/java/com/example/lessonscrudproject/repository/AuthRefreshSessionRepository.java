package com.example.lessonscrudproject.repository;



import com.example.lessonscrudproject.model.AuthRefreshSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRefreshSessionRepository extends CrudRepository<AuthRefreshSession,String> {

}
