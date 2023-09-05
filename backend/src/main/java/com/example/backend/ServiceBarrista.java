package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.data.Roesterei;
import com.example.backend.data.Tasting;
import com.example.backend.data.Zubereitungsmethode;
import com.example.backend.exceptions.*;
import com.example.backend.repos.KaffeesorteRepo;
import com.example.backend.repos.RoestereiRepo;
import com.example.backend.repos.TastingRepo;
import com.example.backend.repos.ZubereitungsmethodeRepo;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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


    //filterbyX für gefilterte Listen

    //Kaffeesorten
    public List<Kaffeesorte> filterKaffeesorten(@Nullable String RoestereiName, @Nullable String Aromenprofil) {
        List<Kaffeesorte> allKaffeesorten = kaffeesorteRepo.findAll();

        return allKaffeesorten.stream()
                .filter(kaffeesorte -> (RoestereiName == null || kaffeesorte.getRoestereiName().equals(RoestereiName))
                        && (Aromenprofil == null || kaffeesorte.getAromenprofil().equals(Aromenprofil)))
                .collect(Collectors.toList());
    }

    //Zubereitungsmethoden
    public List<Zubereitungsmethode> filterbyMethodenType (String MethodenType){
        return zubereitungsmethodeRepo.findByMethodenType(MethodenType);
    }

    //Tastings

    public List<Tasting> filterTastings(@Nullable String zubereitungsmethodeName, @Nullable String kaffeeSorteName, @Nullable String bewertungFrontend) {
        List<Tasting> allTastings = tastingRepo.findAll();

        int startBewertung, endBewertung;

        switch (bewertungFrontend.toLowerCase()) {
            case "schlecht":
                startBewertung = 1;
                endBewertung = 3;
                break;
            case "mittel":
                startBewertung = 4;
                endBewertung = 6;
                break;
            case "gut":
                startBewertung = 7;
                endBewertung = 9;
                break;
            case "top":
                startBewertung = 10;
                endBewertung = 10;
                break;
            default:
                throw new IllegalArgumentException("Invalid Bewertungsangabe: " + bewertungFrontend);
        }

        return allTastings.stream()
                .filter(tasting -> (zubereitungsmethodeName == null || tasting.getZubereitungsmethodeName().equals(zubereitungsmethodeName))
                        && (kaffeeSorteName == null || tasting.getKaffeeSorteName().equals(kaffeeSorteName))
                        && (bewertungFrontend == null || (tasting.getBewertung() >= startBewertung && tasting.getBewertung() <= endBewertung)))
                .collect(Collectors.toList());
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

    /*public List<String> getDistinctMethodenTypes() {
        List<Zubereitungsmethode> methode = zubereitungsmethodeRepo.findMethodenTypes();
        return methode.stream()
                .map(Zubereitungsmethode::getMethodenType)
                .distinct()
                .collect(Collectors.toList());
    }*/

    //Methoden für Formulare, Add, Update, Delete

    //Kaffeesorte
    public Kaffeesorte addKaffeesorte(Kaffeesorte newKaffeesorte) throws KaffeesorteAlreadyExistsExeption {
        Optional<Kaffeesorte> existingKaffeesorte = kaffeesorteRepo.findByKaffeesorteNameAndRoestereiName(
                newKaffeesorte.getKaffeesorteName(),
                newKaffeesorte.getRoestereiName()
        );

        if (existingKaffeesorte.isPresent()) {
            throw new IllegalStateException("Die Kaffeesorte mit dem Namen: " + newKaffeesorte.getKaffeesorteName()
                    + "existiert bereits bei der angegebenen Rösterei.");
        }
        return kaffeesorteRepo.save(newKaffeesorte);
    }

    public Kaffeesorte deleteKaffeesorteById(String Id) throws KaffeesorteDoesNotExistExeption {
        Kaffeesorte removedKaffeesorte = kaffeesorteRepo.findById(Id).orElseThrow(
                () -> new KaffeesorteDoesNotExistExeption("Es gibt keine Kaffeesorte mit der Id: " + Id + "Bitte versuche es noch einmal"));
        kaffeesorteRepo.deleteById(Id);
        return removedKaffeesorte;
    }

    public Kaffeesorte deleteKaffeesorteByKaffeesorteName(String KaffeesorteName) throws KaffeesorteDoesNotExistExeption {
        Kaffeesorte deletedKaffeesorte = kaffeesorteRepo.findByKaffeesorteName(KaffeesorteName).orElseThrow(
                () -> new KaffeesorteDoesNotExistExeption("Es gibt keine Kaffeesorte mit dem Namen: " + KaffeesorteName + "Bitte versuche es noch einmal"));
        kaffeesorteRepo.deleteByKaffeesorteName(KaffeesorteName);
        return deletedKaffeesorte;
    }

    public Kaffeesorte updateKaffeesorteById (String Id, Kaffeesorte updateKaffeesorte) throws KaffeesorteDoesNotExistExeption{
        if (!kaffeesorteRepo.existsById(Id)) {
            throw new KaffeesorteDoesNotExistExeption("Es gibt keine Kaffeesorte mit der ID: " + Id);
        }
        updateKaffeesorte.setId(Id);
        return kaffeesorteRepo.save(updateKaffeesorte);
    }

    //Roesterei

    public Roesterei addRoesterei (Roesterei newRoesterei) throws RoestereiDoesAlreadyExistException {
        Optional<Roesterei> existingRoesterei = roestereiRepo.findByRoestereiName(newRoesterei.getRoestereiName());

        if (existingRoesterei.isPresent()) {
            throw new RoestereiDoesAlreadyExistException ("Die Kaffeesorte mit dem Namen: " + newRoesterei.getRoestereiName()
                    + "existiert bereits bei der angegebenen Rösterei.");
        }
        return roestereiRepo.save(newRoesterei);

    }
    public Roesterei deleteRoestereiById (String Id) throws RoestereiDoesNotExistException {
        Roesterei deletedRoesterei = roestereiRepo.findById(Id).orElseThrow(
                () -> new RoestereiDoesNotExistException("Es gibt keine Rösterei mit der Id: " + Id + "Bitte versuche es noch einmal"));
        roestereiRepo.deleteById(Id);
        return deletedRoesterei;
    }

    public Roesterei updateRoestereiById (String Id, Roesterei updatedRoesterei) throws RoestereiDoesNotExistException {
        if (!roestereiRepo.existsById(Id)) {
            throw new RoestereiDoesNotExistException("Es gibt keine Roesterei mit der ID: " + Id);
        }
        updatedRoesterei.setId(Id);
        return roestereiRepo.save(updatedRoesterei);
    }

    //Tasting

    public Tasting addTasting(Tasting newTasting) throws TastingDoesAlreadyExistException {
        Optional<Tasting> existingTasting = tastingRepo.findByTastingName(newTasting.getTastingName());

        if (existingTasting.isPresent()) {
            throw new TastingDoesAlreadyExistException("Das Tasting mit dem Namen: " + newTasting.getTastingName()
                    + " existiert bereits.");
        }
        return tastingRepo.save(newTasting);
    }

    public Tasting deleteTastingById(String Id) throws TastingDoesNotExistException {
        Tasting deletedTasting = tastingRepo.findById(Id).orElseThrow(
                () -> new TastingDoesNotExistException("Es gibt kein Tasting mit der Id: " + Id + ". Bitte versuche es noch einmal."));
        tastingRepo.deleteById(Id);
        return deletedTasting;
    }

    public Tasting updateTastingById(String Id, Tasting updatedTasting) throws TastingDoesNotExistException {
        if (!tastingRepo.existsById(Id)) {
            throw new TastingDoesNotExistException("Es gibt kein Tasting mit der ID: " + Id);
        }
        updatedTasting.setId(Id);
        return tastingRepo.save(updatedTasting);
    }

    //Zubereitungsmethode
    public Zubereitungsmethode addZubereitungsmethode(Zubereitungsmethode newZubereitungsmethode) throws ZubereitungsmethodeDoesAlreadyExist {
        Optional<Zubereitungsmethode> existingZubereitungsmethode = zubereitungsmethodeRepo.findByZubereitungsmethodeName(newZubereitungsmethode.getZubereitungsmethodeName());

        if (existingZubereitungsmethode.isPresent()) {
            throw new ZubereitungsmethodeDoesAlreadyExist("Die Zubereitungsmethode mit dem Namen: " + newZubereitungsmethode.getZubereitungsmethodeName()
                    + " existiert bereits.");
        }
        return zubereitungsmethodeRepo.save(newZubereitungsmethode);
    }

    public Zubereitungsmethode deleteZubereitungsmethodeById(String Id) throws ZubereitungsmethodeDoesNotExistException {
        Zubereitungsmethode deletedZubereitungsmethode = zubereitungsmethodeRepo.findById(Id).orElseThrow(
                () -> new ZubereitungsmethodeDoesNotExistException("Es gibt keine Zubereitungsmethode mit der Id: " + Id + ". Bitte versuche es noch einmal."));
        zubereitungsmethodeRepo.deleteById(Id);
        return deletedZubereitungsmethode;
    }

    public Zubereitungsmethode updateZubereitungsmethodeById(String Id, Zubereitungsmethode updatedZubereitungsmethode) throws ZubereitungsmethodeDoesNotExistException {
        if (!zubereitungsmethodeRepo.existsById(Id)) {
            throw new ZubereitungsmethodeDoesNotExistException("Es gibt keine Zubereitungsmethode mit der ID: " + Id);
        }
        updatedZubereitungsmethode.setId(Id);
        return zubereitungsmethodeRepo.save(updatedZubereitungsmethode);
    }

}



