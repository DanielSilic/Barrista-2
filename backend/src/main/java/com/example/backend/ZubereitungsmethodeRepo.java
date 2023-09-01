package com.example.backend;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZubereitungsmethodeRepo extends MongoRepository<Zubereitungsmethode, String> {
    List<Zubereitungsmethode> findByMethodenType(String MethodenType);

    @Query(value = "{}", fields = "{ 'MethodenType' : 1}")
    List<Zubereitungsmethode> findMethodenTypes();
}
