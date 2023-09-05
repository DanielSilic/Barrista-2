package com.example.backend.repos;

import com.example.backend.data.Zubereitungsmethode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZubereitungsmethodeRepo extends MongoRepository<Zubereitungsmethode, String> {
    List<Zubereitungsmethode> findByMethodenType(String MethodenType);

    Optional<Zubereitungsmethode> findByZubereitungsmethodeName(String ZubereitungsmethodeName);

    @Query(value = "{}", fields = "{ 'MethodenType' : 1}")
    List<Zubereitungsmethode> findMethodenTypes();
}
