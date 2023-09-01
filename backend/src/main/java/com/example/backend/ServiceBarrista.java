package com.example.backend;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceBarrista {

    private KaffeesorteRepo kaffeesorteRepo;
    private RoestereiRepo roestereiRepo;
    private TastingRepo tastingRepo;
    private ZubereitungsmethodeRepo zubereitungsmethodeRepo;

    //GetALL für Übersichtslisten

    public List<Kaffeesorte> getAllKaffeesorten() {
        return kaffeesorteRepo.findAll();
    }

    public List<Roesterei> getAllRoestereien() {
        return roestereiRepo.findAll();
    }

    public List<Tasting> getAllTastings() {
        return tastingRepo.findAll();
    }

    public List<Zubereitungsmethode> getAllZubereitungsmethoden() {
        return zubereitungsmethodeRepo.findAll();
    }


    //getByX für gefilterte Listen

    public List<Kaffeesorte> filterByRoestereiName(String roestereiName) {
        return kaffeesorteRepo.findByRoestereiName(roestereiName);
    }
    public List<Kaffeesorte> filterByAromenprofil(String aromenprofil) {
        return kaffeesorteRepo.findByAromenprofil(aromenprofil);
    }
    public List<Kaffeesorte> filterbyVariety(String variety) {
        return kaffeesorteRepo.findByVariety(variety);
    }
    public List<Zubereitungsmethode> filterbyMethodenType (String MethodenType){
        return zubereitungsmethodeRepo.findByMethodenType(MethodenType);
    }
    public List<Tasting> filterbyKaffeeSorteName (String KaffeeSorteName){
        return tastingRepo.findByKaffeeSorteName (KaffeeSorteName);
    }
    public List<Tasting> filterbyBewertung (int Bewertung){
        return tastingRepo.findByBewertung(Bewertung);
    }

    //Tastings nach Bewertung
    public List<Tasting> getTastingsByBewertung(String bewertungFrontend) {
        int start, end;

        switch (bewertungFrontend.toLowerCase()) {
            case "schlecht":
                start = 1;
                end = 3;
                break;
            case "mittel":
                start = 4;
                end = 6;
                break;
            case "gut":
                start = 7;
                end = 9;
                break;
            case "top":
                start = 10;
                end = 10;
                break;
            default:
                throw new IllegalArgumentException("Invalide Bewertungsangabe: " + bewertungFrontend);
        }

        return tastingRepo.findByBewertungBetween(start, end);
    }



    //getFilter für Filter-Dropdowns

    public List<String> getAllRoestereiName (){
        List<Roesterei> allRoestereien = roestereiRepo.findAll();
        Set<String> rostereiNamen = new HashSet<>();
        for (Roesterei roesterei : allRoestereien) {
            rostereiNamen.add(roesterei.getRoestereiName());
        }
        return new ArrayList<>(rostereiNamen);
    }

    public List<String> getAllKaffeesorteName (){
        List<Kaffeesorte> allKaffeesorten = kaffeesorteRepo.findAll();
        Set<String> kaffeesortenNamen = new HashSet<>();
        for (Kaffeesorte kaffeesorte : allKaffeesorten) {
            kaffeesortenNamen.add(kaffeesorte.getKaffeesorteName());
        }
        return new ArrayList<>(kaffeesortenNamen);
    }

    public List<String> getAllMethodenType () {
        List<Zubereitungsmethode> allZubereitungsmethoden = getAllZubereitungsmethoden();
        Set<String> methodenTypen = new HashSet<>();
        for (Zubereitungsmethode zubereitungsmethode : allZubereitungsmethoden) {
            methodenTypen.add(zubereitungsmethode.getMethodenType());
        }
        return new ArrayList<>(methodenTypen);
    }

    public List<String> getAllZubereitungsMethodenNamen (){
        List<Zubereitungsmethode> allZubereitungsmethoden = getAllZubereitungsmethoden();
        Set<String> zubereitungsmethodenNamen = new HashSet<>();
        for (Zubereitungsmethode zubereitungsmethode : allZubereitungsmethoden){
            zubereitungsmethodenNamen.add(zubereitungsmethode.getZubereitungsmethodeName());
        }
        return new ArrayList<>(zubereitungsmethodenNamen);
    }

    public List<String> getDistinctMethodenTypes() {
        List<Zubereitungsmethode> methode = zubereitungsmethodeRepo.findMethodenTypes();
        return methode.stream()
                .map(Zubereitungsmethode::getMethodenType)
                .distinct()
                .collect(Collectors.toList());
    }




}



