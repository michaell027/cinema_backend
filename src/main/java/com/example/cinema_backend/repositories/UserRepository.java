package com.example.cinema_backend.repositories;

import com.example.cinema_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(exported = false)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
