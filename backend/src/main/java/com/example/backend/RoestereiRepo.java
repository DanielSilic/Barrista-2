package com.example.backend;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoestereiRepo extends MongoRepository<Roesterei,String> {
}
