package com.example.backend.repos;

import com.example.backend.data.Tasting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TastingRepo extends MongoRepository<Tasting,String> {

    List<Tasting> findByKaffeeSorteName(String KaffeeSorteName);
    List<Tasting> findByZubereitungsmethodeName (String ZubereitungsmethodeName);

    List<Tasting> findByBewertung (int Bewertung);

    List<Tasting> findByBewertungBetween(int start, int end);

    Optional<Tasting> findByTastingName (String TastingName);

}
