package com.WebCrawling.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.WebCrawling.collection.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // On extends MongoRepository pour avoir accès aux méthodes find, find all etc..
    Optional<User> findByEmail(String email); // Optional signifie qu'une valeur peut être absente, omis
}
