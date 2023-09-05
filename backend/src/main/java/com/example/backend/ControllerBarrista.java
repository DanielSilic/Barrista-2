package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.data.Roesterei;
import com.example.backend.data.Tasting;
import com.example.backend.data.Zubereitungsmethode;
import com.example.backend.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/barista")
public class ControllerBarrista {

    private final ServiceBarrista serviceBarrista;

    //Listenseiten, getAll
    @GetMapping("/kaffeesorten")
    public List<Kaffeesorte> getAllKaffeesorten() {
        return serviceBarrista.getAllKaffeesorten();
    }

    @GetMapping("/roesterein")
    public List<Roesterei> getAllRoestereien(){
        return serviceBarrista.getAllRoestereien();
    }

    @GetMapping("/tastings")
    public List<Tasting> getAllTastings(){
        return serviceBarrista.getAllTastings();
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
    public List<Kaffeesorte> getFilteredKaffeesorten(
            @RequestParam(value = "RoestereiName", required = false) String RoestereiName,
            @RequestParam(value = "Aromenprofil", required = false) String Aromenprofil) {

        List<Kaffeesorte> filteredKaffeesorten = serviceBarrista.filterKaffeesorten(RoestereiName, Aromenprofil);

        return filteredKaffeesorten;
    }

    //Tastings
    @GetMapping("/tastings/filter")
    public List<Tasting> filterTastings(
            @RequestParam(required = false) String zubereitungsmethodeName,
            @RequestParam(required = false) String kaffeesorteName,
            @RequestParam(required = false) String bewertungFrontend) {

        List<Tasting> filteredTastings = serviceBarrista.filterTastings(zubereitungsmethodeName, kaffeesorteName, bewertungFrontend);

        return filteredTastings;
    }

    //Zubereitungsmethode
    @GetMapping("/zubereitungsmethoden/filter")
    public List<Zubereitungsmethode> filterZubereitungsmethode(
            @RequestParam String methodenType){
        List <Zubereitungsmethode> filteredZubereitungsmethode = serviceBarrista.filterbyMethodenType(methodenType);

        return filteredZubereitungsmethode;
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

    @PutMapping("/kaffeesorte/{id}")
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

    @PutMapping("/tasting/{id}")
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

}
