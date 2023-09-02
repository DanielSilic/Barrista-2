package com.example.backend.repos;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.data.Roesterei;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoestereiRepo extends MongoRepository<Roesterei,String> {
    Optional<Roesterei> findByRoestereiName(String RoestereiName);

}
