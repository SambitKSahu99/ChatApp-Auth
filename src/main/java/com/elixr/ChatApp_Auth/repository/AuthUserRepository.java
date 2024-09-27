package com.elixr.ChatApp_Auth.repository;

import com.elixr.ChatApp_Auth.model.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends MongoRepository<AuthUser, UUID> {
    Optional<AuthUser> findByUserName(String userName);
}
