package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.exceptions.KaffeesorteAlreadyExistsExeption;
import com.example.backend.exceptions.KaffeesorteDoesNotExistExeption;
import com.example.backend.repos.KaffeesorteRepo;
import com.example.backend.repos.RoestereiRepo;
import com.example.backend.repos.TastingRepo;
import com.example.backend.repos.ZubereitungsmethodeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceBarristaTest {

    //Kaffeesorten
    //deleteKaffeesorteById
    //updateKaffeesorteById


    @Test
    public void getAllKaffeesorten_whenGetAll_thenReturnCorrectList() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        RoestereiRepo roestereiRepo = Mockito.mock(RoestereiRepo.class);
        TastingRepo tastingRepo = Mockito.mock(TastingRepo.class);
        ZubereitungsmethodeRepo zubereitungsmethodeRepo = Mockito.mock(ZubereitungsmethodeRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, roestereiRepo, tastingRepo, zubereitungsmethodeRepo);

        Mockito.when(kaffeesorteRepo.findAll()).thenReturn(List.of(
                new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                        "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                        "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                        "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA"),

                new Kaffeesorte("2", "KaffeesorteB", "RoestereiB", "VarietyB",
                        "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                        "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                        "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB")
        ));

        Assertions.assertEquals(
                List.of(
                        new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA"),

                        new Kaffeesorte("2", "KaffeesorteB", "RoestereiB", "VarietyB",
                                "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                                "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                                "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB")
                ),
                serviceBarrista.getAllKaffeesorten()
        );

        Mockito.verify(kaffeesorteRepo).findAll();
    }

    @Test
    public void filterKaffeesorten_whenFilter_returnCorrectList() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        RoestereiRepo roestereiRepo = Mockito.mock(RoestereiRepo.class);
        TastingRepo tastingRepo = Mockito.mock(TastingRepo.class);
        ZubereitungsmethodeRepo zubereitungsmethodeRepo = Mockito.mock(ZubereitungsmethodeRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, roestereiRepo, tastingRepo, zubereitungsmethodeRepo);

        List<Kaffeesorte> mockKaffeesorten = List.of(
                new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                        "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                        "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                        "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA"),

                new Kaffeesorte("2", "KaffeesorteB", "RoestereiA", "VarietyB",
                        "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                        "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                        "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB")
        );

        Mockito.when(kaffeesorteRepo.findAll()).thenReturn(mockKaffeesorten);

        List<Kaffeesorte> expectedFilteredKaffeesorten = mockKaffeesorten.stream()
                .filter(k -> k.getRoestereiName().equals("RoestereiA") && k.getAromenprofil().equals("AromenprofilB"))
                .collect(Collectors.toList());

        Assertions.assertEquals(
                expectedFilteredKaffeesorten,
                serviceBarrista.filterKaffeesorten("RoestereiA", "AromenprofilB")
        );

        Mockito.verify(kaffeesorteRepo).findAll();
    }

    @Test
    public void getAllKaffeesorteName_whenCalled_thenReturnUniqueKaffeesorteNames() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        List<Kaffeesorte> mockKaffeesorten = List.of(
                new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                        "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                        "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                        "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA"),
                new Kaffeesorte("2", "KaffeesorteA", "RoestereiA", "VarietyB",
                        "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                        "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                        "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB"),
                new Kaffeesorte("2", "KaffeesorteB", "RoestereiB", "VarietyB",
                        "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                        "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                        "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB")

        );

        Mockito.when(kaffeesorteRepo.findAll()).thenReturn(mockKaffeesorten);

        HashSet<String> expectedNamesSet = new HashSet<>();
        for(Kaffeesorte k : mockKaffeesorten) {
            expectedNamesSet.add(k.getKaffeesorteName());
        }
        List<String> expectedNamesList = new ArrayList<>(expectedNamesSet);

        Assertions.assertEquals(
                expectedNamesList,
                serviceBarrista.getAllKaffeesorteName()
        );

        Mockito.verify(kaffeesorteRepo).findAll();
    }

    @Test
    public void addKaffeesorte_whenKaffeesorteAlreadyExists_thenThrowIllegalStateException() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        Kaffeesorte existingKaffeesorte = new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findByKaffeesorteNameAndRoestereiName(existingKaffeesorte.getKaffeesorteName(), existingKaffeesorte.getRoestereiName()))
                .thenReturn(Optional.of(existingKaffeesorte));

        // Assertion: Exception wird geworfen
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> serviceBarrista.addKaffeesorte(existingKaffeesorte)
        );
    }

    @Test
    public void addKaffeesorte_whenKaffeesorteDoesNotExist_thenSaveAndReturnKaffeesorte() throws KaffeesorteAlreadyExistsExeption {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        Kaffeesorte newKaffeesorte = new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findByKaffeesorteNameAndRoestereiName(newKaffeesorte.getKaffeesorteName(), newKaffeesorte.getRoestereiName()))
                .thenReturn(Optional.empty());
        Mockito.when(kaffeesorteRepo.save(newKaffeesorte)).thenReturn(newKaffeesorte);

        // Assertion: keine Exception geworfen
        Kaffeesorte savedKaffeesorte = serviceBarrista.addKaffeesorte(newKaffeesorte);
        Assertions.assertEquals(newKaffeesorte, savedKaffeesorte);
    }

    @Test
    public void deleteKaffeesorteByKaffeesorteName_whenKaffeesorteExists_thenDeleteAndReturnDeletedKaffeesorte() throws KaffeesorteDoesNotExistExeption {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String kaffeesorteNameToDelete = "KaffeesorteA";
        Kaffeesorte existingKaffeesorte = new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findByKaffeesorteName(kaffeesorteNameToDelete))
                .thenReturn(Optional.of(existingKaffeesorte));
        Mockito.doNothing().when(kaffeesorteRepo).deleteByKaffeesorteName(kaffeesorteNameToDelete);

        Kaffeesorte deletedKaffeesorte = serviceBarrista.deleteKaffeesorteByKaffeesorteName(kaffeesorteNameToDelete);
        Assertions.assertEquals(existingKaffeesorte, deletedKaffeesorte);

        Mockito.verify(kaffeesorteRepo).findByKaffeesorteName(kaffeesorteNameToDelete);
        Mockito.verify(kaffeesorteRepo).deleteByKaffeesorteName(kaffeesorteNameToDelete);
    }

    @Test
    public void deleteKaffeesorteByKaffeesorteName_whenKaffeesorteDoesNotExist_thenThrowException() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        Mockito.when(kaffeesorteRepo.findByKaffeesorteName("NonExistentKaffeesorte"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                KaffeesorteDoesNotExistExeption.class,
                () -> serviceBarrista.deleteKaffeesorteByKaffeesorteName("NonExistentKaffeesorte")
        );
    }

    @Test
    public void updateKaffeesorteById_whenKaffeesorteExists_thenUpdateAndReturnKaffeesorte() throws KaffeesorteDoesNotExistExeption {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String kaffeesorteIdToUpdate = "12345";
        Kaffeesorte updatedKaffeesorteInfo = new Kaffeesorte(null, "KaffeesorteB", "RoestereiB", "VarietyB",
                "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB");

        Mockito.when(kaffeesorteRepo.existsById(kaffeesorteIdToUpdate)).thenReturn(true);
        Mockito.when(kaffeesorteRepo.save(updatedKaffeesorteInfo)).thenReturn(updatedKaffeesorteInfo);

        Kaffeesorte updatedKaffeesorte = serviceBarrista.updateKaffeesorteById(kaffeesorteIdToUpdate, updatedKaffeesorteInfo);
        Assertions.assertEquals(updatedKaffeesorteInfo, updatedKaffeesorte);

        Mockito.verify(kaffeesorteRepo).existsById(kaffeesorteIdToUpdate);
        Mockito.verify(kaffeesorteRepo).save(updatedKaffeesorteInfo);
    }

    @Test
    public void updateKaffeesorteById_whenKaffeesorteDoesNotExist_thenThrowException() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String nonExistentKaffeesorteId = "99999";
        Kaffeesorte updatedKaffeesorteInfo = new Kaffeesorte(null, "KaffeesorteB", "RoestereiB", "VarietyB",
                "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB");
        Mockito.when(kaffeesorteRepo.existsById(nonExistentKaffeesorteId)).thenReturn(false);

        Assertions.assertThrows(
                KaffeesorteDoesNotExistExeption.class,
                () -> serviceBarrista.updateKaffeesorteById(nonExistentKaffeesorteId, updatedKaffeesorteInfo)
        );
    }

    @Test
    public void deleteKaffeesorteById_whenKaffeesorteExists_thenRemoveAndReturnRemovedKaffeesorte() throws KaffeesorteDoesNotExistExeption {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);  // Passing null for other repos since they aren't used in this method

        String kaffeesorteIdToRemove = "12345";
        Kaffeesorte kaffeesorteToBeRemoved = new Kaffeesorte(kaffeesorteIdToRemove, "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findById(kaffeesorteIdToRemove)).thenReturn(Optional.of(kaffeesorteToBeRemoved));

        Kaffeesorte removedKaffeesorte = serviceBarrista.deleteKaffeesorteById(kaffeesorteIdToRemove);
        Assertions.assertEquals(kaffeesorteToBeRemoved, removedKaffeesorte);

        Mockito.verify(kaffeesorteRepo).findById(kaffeesorteIdToRemove);
        Mockito.verify(kaffeesorteRepo).deleteById(kaffeesorteIdToRemove);
    }

    @Test
    public void deleteKaffeesorteById_whenKaffeesorteDoesNotExist_thenThrowException() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String kaffeesorteIdToRemove = "12345";

        Mockito.when(kaffeesorteRepo.findById(kaffeesorteIdToRemove)).thenReturn(Optional.empty());

        Assertions.assertThrows(KaffeesorteDoesNotExistExeption.class, () -> {
            serviceBarrista.deleteKaffeesorteById(kaffeesorteIdToRemove);
        });

        Mockito.verify(kaffeesorteRepo).findById(kaffeesorteIdToRemove);
        Mockito.verify(kaffeesorteRepo, Mockito.never()).deleteById(Mockito.anyString());
    }

}































        //Tastings




