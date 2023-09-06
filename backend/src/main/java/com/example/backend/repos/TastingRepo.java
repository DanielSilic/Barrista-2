package com.example.backend.repos;

import com.example.backend.data.Tasting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TastingRepo extends MongoRepository<Tasting,String> {

    Optional<Tasting> findByTastingName(String tastingName);

}
