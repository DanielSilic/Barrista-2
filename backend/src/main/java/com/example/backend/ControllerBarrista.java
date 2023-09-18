package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.data.Roesterei;
import com.example.backend.data.Tasting;
import com.example.backend.data.Zubereitungsmethode;
import com.example.backend.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/barista")
public class ControllerBarrista {

    private final ServiceBarrista serviceBarrista;

    //Listenseiten, getAll
    @GetMapping("/kaffeesorte")
    public Page<Kaffeesorte> getAllKaffeesorten(Pageable pageable) {
        return serviceBarrista.getAllKaffeesorten(pageable);
    }

    @GetMapping("/roestereien")
    public List<Roesterei> getAllRoestereien(){
        return serviceBarrista.getAllRoestereien();
    }

    @GetMapping("/tastings")
    public Page<Tasting> getAllTastings(Pageable pageable) {
        return serviceBarrista.getAllTastings(pageable);
    }

    @GetMapping("/zubereitungsmethoden")
    public List<Zubereitungsmethode> getAllZubereitungsmethoden(){
        return serviceBarrista.getAllZubereitungsmethoden();
    }

    //Listenseiten alle Dropdown-Werte
    @GetMapping("/dropdown/roestereiname")
    public List<String> getAllRoestereiName(){
        return serviceBarrista.getAllRoestereiName();
    }

    @GetMapping("/dropdown/kaffeesortename")
    public List<String> getAllKaffeesorteName(){
        return serviceBarrista.getAllKaffeesorteName();
    }

    @GetMapping("/dropdown/methodentype")
    public List<String> getAllMethodenType(){
        return serviceBarrista.getAllMethodenType();
    }

    @GetMapping("/dropdown/zubereitungsmethodennamen")
    public List<String> getAllZubereitungsMethodenNamen(){
        return serviceBarrista.getAllZubereitungsMethodenNamen();
    }


    //Listenseiten gefiltert

    //Kaffeesorten
    @GetMapping("/kaffeesorten/filter")
    public ResponseEntity<Map<String, Object>> getFilteredKaffeesorten(
            @RequestParam(value = "roestereiName", required = false) String roestereiName,
            @RequestParam(value = "aromenProfil", required = false) String aromenProfil,
            Pageable pageable) {

        Page<Kaffeesorte> page = serviceBarrista.filterKaffeesorten(roestereiName, aromenProfil, pageable);

        List<Kaffeesorte> kaffeesorten = page.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("kaffeesorten", kaffeesorten);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Tastings
    @GetMapping("/tastings/filter")
    public ResponseEntity<Map<String, Object>> filterTastings(
            @RequestParam(required = false) String zubereitungsmethodeName,
            @RequestParam(required = false) String kaffeesorteName,
            @RequestParam(required = false) String bewertungFrontend,
            Pageable pageable) {

        Page<Tasting> page = serviceBarrista.filterTastings(zubereitungsmethodeName, kaffeesorteName, bewertungFrontend, pageable);

        List<Tasting> tastings = page.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("tastings", tastings);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Zubereitungsmethode
    @GetMapping("/zubereitungsmethoden/methodentype")
    public List<Zubereitungsmethode> findZMByMethodentype(@RequestParam String methodenType) {
        return serviceBarrista.filterbyMethodenType(methodenType);
    }

    //Liste von Kaffeesorten für RöstereiDetailseite
    @GetMapping("/kaffeesorte/nachroesterei")
    public List<Kaffeesorte> getAllKaffeesortenByRoesterei(
            @RequestParam String roestereiName){
        return serviceBarrista.getAllKaffeesortenByRoesterei (roestereiName);
    }

    //Formularseiten
    //Kaffeesorte

    @PostMapping("/newkaffeesorte")
    public Kaffeesorte addKaffeesorte(@RequestBody Kaffeesorte newKaffeesorte) throws KaffeesorteAlreadyExistsException {
        return serviceBarrista.addKaffeesorte(newKaffeesorte);
    }

    @DeleteMapping("/kaffeesorte/{id}")
    public Kaffeesorte deleteKaffeesorteById(@PathVariable String id) throws KaffeesorteDoesNotExistException {
        return serviceBarrista.deleteKaffeesorteById(id);
    }

    @PutMapping("/updatedkaffeesorte/{id}")
    public Kaffeesorte updateKaffeesorteById(@PathVariable String id, @RequestBody Kaffeesorte updatedKaffeesorte) throws KaffeesorteDoesNotExistException {
        return serviceBarrista.updateKaffeesorteById(id, updatedKaffeesorte);
    }

    //Röstereien

    @PostMapping("/roesterei")
    public Roesterei addRoesterei(@RequestBody Roesterei newRoesterei) throws RoestereiDoesAlreadyExistException {
        return serviceBarrista.addRoesterei(newRoesterei);
    }

    @DeleteMapping("/roesterei/{id}")
    public Roesterei deleteRoestereiById(@PathVariable String id) throws RoestereiDoesNotExistException {
        return serviceBarrista.deleteRoestereiById(id);
    }

    @PutMapping("/roesterei/{id}")
    public Roesterei updateRoestereiById(@PathVariable String id, @RequestBody Roesterei updatedRoesterei) throws RoestereiDoesNotExistException {
        return serviceBarrista.updateRoestereiById(id, updatedRoesterei);
    }


    //Tastings

    @PostMapping("/tasting")
    public Tasting addTasting(@RequestBody Tasting newTasting) throws TastingDoesAlreadyExistException {
        return serviceBarrista.addTasting(newTasting);
    }

    @DeleteMapping("/tasting/{id}")
    public Tasting deleteTastingById(@PathVariable String id) throws TastingDoesNotExistException {
        return serviceBarrista.deleteTastingById(id);
    }

    @PutMapping("/updatedtasting/{id}")
    public Tasting updateTastingById(@PathVariable String id, @RequestBody Tasting updatedTasting) throws TastingDoesNotExistException {
        return serviceBarrista.updateTastingById(id, updatedTasting);
    }

    //Zubereitungsmethoden

    @PostMapping("/zubereitungsmethode")
    public Zubereitungsmethode addZubereitungsmethode(@RequestBody Zubereitungsmethode newZubereitungsmethode) throws ZubereitungsmethodeDoesAlreadyExist {
        return serviceBarrista.addZubereitungsmethode(newZubereitungsmethode);
    }

    @DeleteMapping("/zubereitungsmethode/{id}")
    public Zubereitungsmethode deleteZubereitungsmethodeById(@PathVariable String id) throws ZubereitungsmethodeDoesNotExistException {
        return serviceBarrista.deleteZubereitungsmethodeById(id);
    }

    @PutMapping("/zubereitungsmethode/{id}")
    public Zubereitungsmethode updateZubereitungsmethodeById(@PathVariable String id, @RequestBody Zubereitungsmethode updatedZubereitungsmethode) throws ZubereitungsmethodeDoesNotExistException {
        return serviceBarrista.updateZubereitungsmethodeById(id, updatedZubereitungsmethode);
    }

    //Detailseiten

    //Kaffeesorte
    @GetMapping("/kaffeesorte/{id}")
    public Kaffeesorte getKaffeesorteById(@PathVariable String id) throws KaffeesorteDoesNotExistException {
        return serviceBarrista.getKaffeesorteById(id);
    }

    //Tasting
    @GetMapping("/tasting/{id}")
    public Tasting getTastingById(@PathVariable String id) throws TastingDoesNotExistException {
        return serviceBarrista.getTastingById(id);
    }

    //Roesterei
    @GetMapping("/roesterei/{roestereiName}")
    public Roesterei getRoestereiById(@PathVariable String roestereiName) throws RoestereiDoesNotExistException {
        return serviceBarrista.getRoestereiByName(roestereiName);
    }

}
