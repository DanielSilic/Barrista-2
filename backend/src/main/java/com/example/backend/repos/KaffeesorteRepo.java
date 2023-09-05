package com.example.backend.repos;

import com.example.backend.data.Kaffeesorte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KaffeesorteRepo extends MongoRepository<Kaffeesorte,String> {

    List<Kaffeesorte> findByRoestereiName(String RoestereiName);
    List<Kaffeesorte> findByAromenprofil(String Aromenprofil);
    List<Kaffeesorte> findByVariety(String Variety);
    Optional<Kaffeesorte> findByKaffeesorteName(String KaffeesorteName);
    Optional<Kaffeesorte> findByKaffeesorteNameAndRoestereiName(String kaffeesorteName, String roestereiName);
    void deleteByKaffeesorteName(String kaffeesorteName);
}
