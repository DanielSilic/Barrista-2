package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.exceptions.KaffeesorteAlreadyExistsException;
import com.example.backend.exceptions.KaffeesorteDoesNotExistException;
import com.example.backend.repos.KaffeesorteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceBarristaTest {

    //Kaffeesorten
    //deleteKaffeesorteById
    //updateKaffeesorteById


    @Test
    public void getAllKaffeesorten_whenGetAll_thenReturnCorrectList() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista (kaffeesorteRepo, null, null, null);

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
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        List<Kaffeesorte> mockKaffeesorten = List.of(
                new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                        "AufbereitungA", "LandA", "AromenA", "AromenprofilA, AromenprofilB",
                        "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                        "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA"),
                new Kaffeesorte("2", "KaffeesorteB", "RoestereiA", "VarietyB",
                        "AufbereitungB", "LandB", "AromenB", "AromenprofilB, AromenprofilC",
                        "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                        "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB")
        );

        Pageable mockPageable = PageRequest.of(0, 10);
        Page<Kaffeesorte> mockPageKaffeesorten = new PageImpl<>(mockKaffeesorten);

        Mockito.when(kaffeesorteRepo.findAll(mockPageable)).thenReturn(mockPageKaffeesorten);

        List<Kaffeesorte> expectedFilteredKaffeesorten = mockKaffeesorten.stream()
                .filter(k -> k.getRoestereiName().equals("RoestereiA") && Arrays.asList(k.getAromenProfil().split(", ")).contains("AromenprofilB"))
                .collect(Collectors.toList());

        Page<Kaffeesorte> expectedPage = new PageImpl<>(expectedFilteredKaffeesorten, mockPageable, expectedFilteredKaffeesorten.size());

        Assertions.assertEquals(
                expectedPage,
                serviceBarrista.filterKaffeesorten("RoestereiA", "AromenprofilB", mockPageable)
        );

        Mockito.verify(kaffeesorteRepo).findAll(mockPageable);
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
    public void addKaffeesorte_whenKaffeesorteAlreadyExists_thenThrowKaffeesorteAlreadyExistsException() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        Kaffeesorte existingKaffeesorte = new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findByKaffeesorteNameAndRoestereiName(existingKaffeesorte.getKaffeesorteName(), existingKaffeesorte.getRoestereiName()))
                .thenReturn(Optional.of(existingKaffeesorte));

        Assertions.assertThrows(
                KaffeesorteAlreadyExistsException.class,
                () -> serviceBarrista.addKaffeesorte(existingKaffeesorte)
        );
    }

    @Test
    public void addKaffeesorte_whenKaffeesorteDoesNotExist_thenSaveAndReturnKaffeesorte() throws KaffeesorteAlreadyExistsException {
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
    public void deleteKaffeesorteByKaffeesorteName_whenKaffeesorteExists_thenDeleteAndReturnDeletedKaffeesorte() throws KaffeesorteDoesNotExistException {
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
                KaffeesorteDoesNotExistException.class,
                () -> serviceBarrista.deleteKaffeesorteByKaffeesorteName("NonExistentKaffeesorte")
        );
    }

    @Test
    public void updateKaffeesorteById_whenKaffeesorteExists_thenUpdateAndReturnKaffeesorte() throws KaffeesorteDoesNotExistException {
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
                KaffeesorteDoesNotExistException.class,
                () -> serviceBarrista.updateKaffeesorteById(nonExistentKaffeesorteId, updatedKaffeesorteInfo)
        );
    }

    @Test
    public void deleteKaffeesorteById_whenKaffeesorteExists_thenRemoveAndReturnRemovedKaffeesorte() throws KaffeesorteDoesNotExistException {
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

        Assertions.assertThrows(KaffeesorteDoesNotExistException.class,
                () -> serviceBarrista.deleteKaffeesorteById(kaffeesorteIdToRemove));


        Mockito.verify(kaffeesorteRepo).findById(kaffeesorteIdToRemove);
        Mockito.verify(kaffeesorteRepo, Mockito.never()).deleteById(Mockito.anyString());
    }

}































        //Tastings




