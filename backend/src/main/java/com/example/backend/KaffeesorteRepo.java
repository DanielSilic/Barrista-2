package com.example.backend;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KaffeesorteRepo extends MongoRepository<Kaffeesorte,String> {

    List<Kaffeesorte> findByRoestereiName(String RoestereiName);
    List<Kaffeesorte> findByAromenprofil(String Aromenprofil);
    List<Kaffeesorte> findByVariety(String Variety);

}
