package com.example.backend.repos;

import com.example.backend.data.Kaffeesorte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KaffeesorteRepo extends MongoRepository<Kaffeesorte,String> {

    Optional<Kaffeesorte> findByKaffeesorteNameAndRoestereiName(String kaffeesorteName, String roestereiName);
    List<Kaffeesorte> findByRoestereiName(String roestereiName);

    Optional<Kaffeesorte> findKaffeesorteById(String id);
}
