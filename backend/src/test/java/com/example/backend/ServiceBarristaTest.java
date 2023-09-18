package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.data.Tasting;
import com.example.backend.data.Zubereitungsmethode;
import com.example.backend.exceptions.KaffeesorteAlreadyExistsException;
import com.example.backend.exceptions.KaffeesorteDoesNotExistException;
import com.example.backend.repos.KaffeesorteRepo;
import com.example.backend.repos.TastingRepo;
import com.example.backend.repos.ZubereitungsmethodeRepo;
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

    //Kaffeesorten: ALL
    //filterTastings


    @Test
    public void getAllKaffeesorten_whenGetAll_thenReturnCorrectList() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista (kaffeesorteRepo, null, null, null);

        // Step 1
        Mockito.when(kaffeesorteRepo.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(
                        new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA"),

                        new Kaffeesorte("2", "KaffeesorteB", "RoestereiB", "VarietyB",
                                "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                                "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                                "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB")
                )));

        Pageable pageable = PageRequest.of(0, 10);

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
                serviceBarrista.getAllKaffeesorten(pageable).getContent()
        );

        Mockito.verify(kaffeesorteRepo).findAll(Mockito.any(Pageable.class));
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

        Mockito.when(kaffeesorteRepo.findAll()).thenReturn(mockKaffeesorten);

        List<Kaffeesorte> expectedFilteredKaffeesorten = mockKaffeesorten.stream()
                .filter(k -> k.getRoestereiName().equals("RoestereiA") && Arrays.asList(k.getAromenProfil().split(", ")).contains("AromenprofilB"))
                .collect(Collectors.toList());

        int start = (int) mockPageable.getOffset();
        int end = Math.min((start + mockPageable.getPageSize()), expectedFilteredKaffeesorten.size());
        List<Kaffeesorte> paginatedList = expectedFilteredKaffeesorten.subList(start, end);

        Page<Kaffeesorte> expectedPage = new PageImpl<>(paginatedList, mockPageable, expectedFilteredKaffeesorten.size());

        Assertions.assertEquals(
                expectedPage,
                serviceBarrista.filterKaffeesorten("RoestereiA", "AromenprofilB", mockPageable)
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

        Set<String> expectedNamesSet = new HashSet<>();
        for(Kaffeesorte k : mockKaffeesorten) {
            expectedNamesSet.add(k.getKaffeesorteName());
        }
        List<String> expectedNamesList = new ArrayList<>(expectedNamesSet);
        Collections.sort(expectedNamesList);

        List<String> actualNamesList = serviceBarrista.getAllKaffeesorteName();
        Collections.sort(actualNamesList);

        Assertions.assertIterableEquals(
                expectedNamesList,
                actualNamesList
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

        Kaffeesorte savedKaffeesorte = serviceBarrista.addKaffeesorte(newKaffeesorte);
        Assertions.assertEquals(newKaffeesorte, savedKaffeesorte);
    }

    @Test
    public void addKaffeesorte_whenKaffeesorteWithSameNameAndDifferentRoesterei_thenSaveAndReturnKaffeesorte() throws KaffeesorteAlreadyExistsException {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        Kaffeesorte newKaffeesorte = new Kaffeesorte("1", "KaffeesorteA", "RoestereiB", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findByKaffeesorteNameAndRoestereiName(newKaffeesorte.getKaffeesorteName(), newKaffeesorte.getRoestereiName()))
                .thenReturn(Optional.empty());

        Mockito.when(kaffeesorteRepo.save(newKaffeesorte)).thenReturn(newKaffeesorte);

        Kaffeesorte savedKaffeesorte = serviceBarrista.addKaffeesorte(newKaffeesorte);

        Assertions.assertEquals(newKaffeesorte, savedKaffeesorte);
    }

    @Test
    public void updateKaffeesorteById_whenKaffeesorteExists_thenUpdateAndReturnKaffeesorte() throws KaffeesorteDoesNotExistException {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String kaffeesorteIdToUpdate = "1";
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

        String nonExistentKaffeesorteId = "1";
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
    public void deleteKaffeesorteById_whenKaffeesorteExists_thenDeleteAndReturnDeletedKaffeesorte() throws KaffeesorteDoesNotExistException {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String kaffeesorteIdToDelete = "1";
        Kaffeesorte existingKaffeesorte = new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findById(kaffeesorteIdToDelete))
                .thenReturn(Optional.of(existingKaffeesorte));
        Mockito.doNothing().when(kaffeesorteRepo).deleteById(kaffeesorteIdToDelete);

        Kaffeesorte deletedKaffeesorte = serviceBarrista.deleteKaffeesorteById(kaffeesorteIdToDelete);

        Assertions.assertEquals(existingKaffeesorte, deletedKaffeesorte);

        Mockito.verify(kaffeesorteRepo).findById(kaffeesorteIdToDelete);
        Mockito.verify(kaffeesorteRepo).deleteById(kaffeesorteIdToDelete);
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

    @Test
    public void getAllKaffeesortenByRoesterei_thenReturnAllKaffeesortenOfRoesterei() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String roestereiNameToQuery = "RoestereiA";
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

        Mockito.when(kaffeesorteRepo.findByRoestereiName(roestereiNameToQuery)).thenReturn(mockKaffeesorten);
        List<Kaffeesorte> fetchedKaffeesorten = serviceBarrista.getAllKaffeesortenByRoesterei(roestereiNameToQuery);

        Assertions.assertEquals(mockKaffeesorten, fetchedKaffeesorten);

        Mockito.verify(kaffeesorteRepo).findByRoestereiName(roestereiNameToQuery);
    }

    @Test
    public void getAllKaffeesorteName_thenReturnUniqueKaffeesorteNames() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        List<Kaffeesorte> mockKaffeesorten = List.of(
                new Kaffeesorte("1", "KaffeesorteA", "RoestereiA", "VarietyA",
                        "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                        "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                        "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA"),
                new Kaffeesorte("2", "KaffeesorteA", "RoestereiB", "VarietyB",
                        "AufbereitungB", "LandB", "AromenB", "AromenprofilB",
                        "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                        "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB"),
                new Kaffeesorte("3", "KaffeesorteB", "RoestereiC", "VarietyC",
                        "AufbereitungC", "LandC", "AromenC", "AromenprofilC",
                        "KoerperC", "SuesseC", "GeschmacksnotenHeissC", "GeschmacksnotenMediumC",
                        "GeschmacksnotenKaltC", "FreezingDateC", "FotoUrlKaffeesorteC")
        );

        Mockito.when(kaffeesorteRepo.findAll()).thenReturn(mockKaffeesorten);

        Set<String> expectedNamesSet = new HashSet<>();
        for(Kaffeesorte k : mockKaffeesorten) {
            expectedNamesSet.add(k.getKaffeesorteName());
        }
        List<String> expectedNamesList = new ArrayList<>(expectedNamesSet);

        List<String> actualNamesList = serviceBarrista.getAllKaffeesorteName();

        Assertions.assertEquals(expectedNamesList, actualNamesList);

        Mockito.verify(kaffeesorteRepo).findAll();
    }

    @Test
    public void getKaffeesorteById_whenKaffeesorteExists_thenReturnKaffeesorte() throws KaffeesorteDoesNotExistException {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String kaffeesorteId = "1";
        Kaffeesorte existingKaffeesorte = new Kaffeesorte(kaffeesorteId, "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Mockito.when(kaffeesorteRepo.findKaffeesorteById(kaffeesorteId)).thenReturn(Optional.of(existingKaffeesorte));

        Kaffeesorte retrievedKaffeesorte = serviceBarrista.getKaffeesorteById(kaffeesorteId);

        Assertions.assertEquals(existingKaffeesorte, retrievedKaffeesorte);

        Mockito.verify(kaffeesorteRepo).findKaffeesorteById(kaffeesorteId);
    }

    @Test
    public void getKaffeesorteById_whenKaffeesorteDoesNotExist_thenThrowException() {
        KaffeesorteRepo kaffeesorteRepo = Mockito.mock(KaffeesorteRepo.class);
        ServiceBarrista serviceBarrista = new ServiceBarrista(kaffeesorteRepo, null, null, null);

        String kaffeesorteId = "2";

        Mockito.when(kaffeesorteRepo.findKaffeesorteById(kaffeesorteId)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                KaffeesorteDoesNotExistException.class,
                () -> serviceBarrista.getKaffeesorteById(kaffeesorteId)
        );

        Mockito.verify(kaffeesorteRepo).findKaffeesorteById(kaffeesorteId);
    }

    @Test
    public void filterTastings_withValidFilterCriteria_shouldReturnFilteredAndPaginatedResults() {
        List<Tasting> allTastings = Arrays.asList(
                new Tasting("1", "Tasting1", "KaffeesorteA", "Espresso", "Mittel", "Rao", "90C", "mittel", 5, "Des is des"),
                new Tasting("2", "Tasting2", "KaffeesorteA", "AeroPress", "Fein", "Melbourne", "95C", "sehr gut", 8, "Nächstes Mal mit mehr Schlach"),
                new Tasting("3", "Tasting3", "KaffeesorteB", "Pour Over", "Grob", "Spring", "92C", "schlecht", 2, "brrr"),
                new Tasting("4", "Tasting4", "KaffeesorteB", "Espresso", "Medium-Fein", "Rao", "94C", "exzellent", 10, "mmmh")
        );

        Pageable mockPageable = PageRequest.of(0, 10);

        TastingRepo tastingRepo = Mockito.mock(TastingRepo.class);
        Mockito.when(tastingRepo.findAll()).thenReturn(allTastings);

        ServiceBarrista serviceBarrista = new ServiceBarrista(null, null, tastingRepo, null);

        Page<Tasting> filteredTastings = serviceBarrista.filterTastings("Espresso", "KaffeesorteA", "mittel", mockPageable);

        Assertions.assertEquals(1, filteredTastings.getContent().size());
        Assertions.assertEquals(1, filteredTastings.getTotalElements());

        Tasting actualTasting = filteredTastings.getContent().get(0);
        Assertions.assertEquals("Espresso", actualTasting.getZubereitungsmethodeName());
        Assertions.assertEquals("KaffeesorteA", actualTasting.getKaffeesorteName());
        Assertions.assertEquals(5, actualTasting.getBewertung());
    }

    @Test
    public void filterbyMethodenType_whenMethodenTypeExists_shouldReturnMatchingResults() {
        List<Zubereitungsmethode> allMethoden = Arrays.asList(
                new Zubereitungsmethode("1", "Espresso", "Eva", "Press", "Beschreibung1", "url1"),
                new Zubereitungsmethode("2", "AeroPress", "Jane", "Press", "Beschreibung2", "url2"),
                new Zubereitungsmethode("3", "Pour Over", "Dan", "Drip", "Beschreibung3", "url3"),
                new Zubereitungsmethode("4", "Cold Brew", "Gustav", "Steep", "Beschreibung4", "url4")
        );

        ZubereitungsmethodeRepo zubereitungsmethodeRepo = Mockito.mock(ZubereitungsmethodeRepo.class);
        Mockito.when(zubereitungsmethodeRepo.findByMethodenType("Press")).thenReturn(
                allMethoden.stream().filter(m -> "Press".equals(m.getMethodenType())).collect(Collectors.toList())
        );

        ServiceBarrista serviceBarrista = new ServiceBarrista(null, null, null, zubereitungsmethodeRepo);

        List<Zubereitungsmethode> filteredMethoden = serviceBarrista.filterbyMethodenType("Press");

        Assertions.assertEquals(2, filteredMethoden.size());
        Assertions.assertTrue(
                filteredMethoden.stream().allMatch(m -> "Press".equals(m.getMethodenType()))
        );
    }

}




































