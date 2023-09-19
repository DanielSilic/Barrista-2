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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<Kaffeesorte> getAllKaffeesorten(Pageable pageable) {
        return kaffeesorteRepo.findAll(pageable);
    }

    public List<Roesterei> getAllRoestereien() {
        return roestereiRepo.findAll();
    }

    public Page<Tasting> getAllTastings(Pageable pageable) {
        return tastingRepo.findAll(pageable);
    }

    public List<Zubereitungsmethode> getAllZubereitungsmethoden() {
        return zubereitungsmethodeRepo.findAll();
    }


    //filter filter filter

    //Filter Kaffeesorten für RoestereiDetailseite
    public List<Kaffeesorte> getAllKaffeesortenByRoesterei(String roestereiName) {
        return kaffeesorteRepo.findByRoestereiName(roestereiName);
    }

    //Filter für Listenseiten

    //Kaffeesorten
    public Page<Kaffeesorte> filterKaffeesorten(@Nullable String roestereiName, @Nullable String aromenProfil, Pageable pageable) {
        List<Kaffeesorte> allKaffeesortenList = kaffeesorteRepo.findAll();

        List<Kaffeesorte> filteredList = allKaffeesortenList.stream()
                .filter(kaffeesorte ->
                        (roestereiName == null || kaffeesorte.getRoestereiName().equals(roestereiName))
                                &&
                                (aromenProfil == null || Arrays.asList(kaffeesorte.getAromenProfil().split(", ")).contains(aromenProfil))
                )
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredList.size());
        List<Kaffeesorte> paginatedList = filteredList.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, filteredList.size());
    }

    //Zubereitungsmethoden
    public List<Zubereitungsmethode> filterbyMethodenType (String methodenType){
        return zubereitungsmethodeRepo.findByMethodenType(methodenType);
    }

    //Tastings
    public Page<Tasting> filterTastings(@Nullable String zubereitungsmethodeName,
                                        @Nullable String kaffeesorteName,
                                        @Nullable String bewertungFrontend,
                                        Pageable pageable) {

        List<Tasting> allTastings = tastingRepo.findAll();

        int startBewertung;
        int endBewertung;

        if (bewertungFrontend != null) {
            switch (bewertungFrontend) {
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
        } else {
            startBewertung = 1;
            endBewertung = 10;
        }

        List<Tasting> filteredTastings = allTastings.stream()
                .filter(tasting -> {
                    String dbValue = tasting.getZubereitungsmethodeName().trim().toLowerCase();
                    String filterValue = (zubereitungsmethodeName != null) ? zubereitungsmethodeName.trim().toLowerCase() : null;

                    return (filterValue == null || dbValue.equals(filterValue))
                            && (kaffeesorteName == null || tasting.getKaffeesorteName().trim().equalsIgnoreCase(kaffeesorteName.trim()))
                            && (tasting.getBewertung() >= startBewertung && tasting.getBewertung() <= endBewertung);
                })
                .collect(Collectors.toList());


        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredTastings.size());
        List<Tasting> paginatedList = filteredTastings.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, filteredTastings.size());
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

    //Methoden für Formulare, Add, Update, Delete
    //Kaffeesorte
    public Kaffeesorte addKaffeesorte(Kaffeesorte newKaffeesorte) throws KaffeesorteAlreadyExistsException {
        Optional<Kaffeesorte> existingKaffeesorte = kaffeesorteRepo.findByKaffeesorteNameAndRoestereiName(
                newKaffeesorte.getKaffeesorteName(),
                newKaffeesorte.getRoestereiName()
        );

        if (existingKaffeesorte.isPresent()) {
            throw new KaffeesorteAlreadyExistsException("Die Kaffeesorte mit dem Namen: " + newKaffeesorte.getKaffeesorteName()
                    + "existiert bereits bei der angegebenen Rösterei.");
        }
        return kaffeesorteRepo.save(newKaffeesorte);
    }

    public Kaffeesorte deleteKaffeesorteById(String id) throws KaffeesorteDoesNotExistException {
        Kaffeesorte removedKaffeesorte = kaffeesorteRepo.findById(id).orElseThrow(
                () -> new KaffeesorteDoesNotExistException("Es gibt keine Kaffeesorte mit der id: " + id + "Bitte versuche es noch einmal"));
        kaffeesorteRepo.deleteById(id);
        return removedKaffeesorte;
    }

    public Kaffeesorte updateKaffeesorteById (String id, Kaffeesorte updateKaffeesorte) throws KaffeesorteDoesNotExistException {
        if (!kaffeesorteRepo.existsById(id)) {
            throw new KaffeesorteDoesNotExistException("Es gibt keine Kaffeesorte mit der ID: " + id);
        }
        updateKaffeesorte.setId(id);
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
    public Roesterei deleteRoestereiById (String id) throws RoestereiDoesNotExistException {
        Roesterei deletedRoesterei = roestereiRepo.findById(id).orElseThrow(
                () -> new RoestereiDoesNotExistException("Es gibt keine Rösterei mit der id: " + id + "Bitte versuche es noch einmal"));
        roestereiRepo.deleteById(id);
        return deletedRoesterei;
    }

    public Roesterei updateRoestereiById (String id, Roesterei updatedRoesterei) throws RoestereiDoesNotExistException {
        if (!roestereiRepo.existsById(id)) {
            throw new RoestereiDoesNotExistException("Es gibt keine Roesterei mit der ID: " + id);
        }
        updatedRoesterei.setId(id);
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

    public Tasting deleteTastingById(String id) throws TastingDoesNotExistException {
        Tasting deletedTasting = tastingRepo.findById(id).orElseThrow(
                () -> new TastingDoesNotExistException("Es gibt kein Tasting mit der id: " + id + ". Bitte versuche es noch einmal."));
        tastingRepo.deleteById(id);
        return deletedTasting;
    }

    public Tasting updateTastingById(String id, Tasting updatedTasting) throws TastingDoesNotExistException {
        if (!tastingRepo.existsById(id)) {
            throw new TastingDoesNotExistException("Es gibt kein Tasting mit der ID: " + id);
        }
        updatedTasting.setId(id);
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

    public Zubereitungsmethode deleteZubereitungsmethodeById(String id) throws ZubereitungsmethodeDoesNotExistException {
        Zubereitungsmethode deletedZubereitungsmethode = zubereitungsmethodeRepo.findById(id).orElseThrow(
                () -> new ZubereitungsmethodeDoesNotExistException("Es gibt keine Zubereitungsmethode mit der id: " + id + ". Bitte versuche es noch einmal."));
        zubereitungsmethodeRepo.deleteById(id);
        return deletedZubereitungsmethode;
    }

    public Zubereitungsmethode updateZubereitungsmethodeById(String id, Zubereitungsmethode updatedZubereitungsmethode) throws ZubereitungsmethodeDoesNotExistException {
        if (!zubereitungsmethodeRepo.existsById(id)) {
            throw new ZubereitungsmethodeDoesNotExistException("Es gibt keine Zubereitungsmethode mit der ID: " + id);
        }
        updatedZubereitungsmethode.setId(id);
        return zubereitungsmethodeRepo.save(updatedZubereitungsmethode);
    }

    //Find individual Item for Detailseite

    //Kaffeesorte
    public Kaffeesorte getKaffeesorteById (String id) throws KaffeesorteDoesNotExistException {
        return kaffeesorteRepo.findKaffeesorteById(id).orElseThrow(() -> new KaffeesorteDoesNotExistException("Es gibt keine Kaffeesorte mit der ID: " + id));
    }

    //Tasting
    public Tasting getTastingById (String id) throws TastingDoesNotExistException {
        return tastingRepo.findTastingById(id).orElseThrow(() -> new TastingDoesNotExistException("Es gibt keine Tasting mit der ID: " + id));
    }

    //Rösterei
    public Roesterei getRoestereiByName (String roestereiName) throws RoestereiDoesNotExistException {
        return roestereiRepo.findByRoestereiName(roestereiName).orElseThrow(() -> new RoestereiDoesNotExistException("Es gibt keine Rösterei mit dem Namen: " + roestereiName));
    }

}



