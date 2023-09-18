package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.data.Tasting;
import com.example.backend.repos.KaffeesorteRepo;
import com.example.backend.repos.TastingRepo;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BarristaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private KaffeesorteRepo kaffeesorteRepo;

    @Autowired
    private TastingRepo tastingRepo;

    @Test
    @DirtiesContext
    @WithMockUser(username = "Dan", password = "123")
    void getAllKaffeesorten_shouldReturnEmptyList_whenDatabaseIsEmpty() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/barista/kaffeesorte")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(0));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Dan", password = "123")
    void getAllKaffeesorten_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        Kaffeesorte kaffeesorte = new Kaffeesorte("123456", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");
        kaffeesorteRepo.save(kaffeesorte);

        mvc.perform(MockMvcRequestBuilders.get("/barista/kaffeesorte")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value("123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].kaffeesorteName").value("KaffeesorteA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].roestereiName").value("RoestereiA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1));
    }


    @Test
    @DirtiesContext
    @WithMockUser(username = "Dan", password = "123")
    public void testAddKaffeesorte_Duplicate_ThrowsException() throws Exception {
        Kaffeesorte existingKaffeesorte = new Kaffeesorte("123456","KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        kaffeesorteRepo.save(existingKaffeesorte);

        mvc.perform(MockMvcRequestBuilders.post("/barista/newkaffeesorte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                 {
                                     "id": "123456",
                                     "kaffeesorteName": "KaffeesorteA",
                                     "roestereiName": "RoestereiA",
                                     "variety": "VarietyA",
                                     "aufbereitung": "AufbereitungA",
                                     "herkunftsland": "LandA",
                                     "aromen": "AromenA",
                                     "aromenProfil": "UpdatedAromenProfil",
                                     "koerper": "KoerperA",
                                     "suesse": "SuesseA",
                                     "geschmacksnotenHeiss": "GeschmacksnotenHeissA",
                                     "geschmacksnotenMedium": "GeschmacksnotenMediumA",
                                     "geschmacksnotenKalt": "GeschmacksnotenKaltA",
                                     "freezingDate": "FreezingDateA",
                                     "fotoUrlKaffeesorte": "FotoUrlKaffeesorteA"
                                 }
                                """
                        )
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }




    @DirtiesContext
    @Test
    @WithMockUser(username = "Dan", password = "123")
    void deleteKaffeesorte_shouldReturnDeletedKaffeesorte() throws Exception {
        Kaffeesorte kaffeesorte = new Kaffeesorte("123456","KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");
        kaffeesorteRepo.save(kaffeesorte);

        mvc.perform(MockMvcRequestBuilders.delete("/barista/kaffeesorte/123456")
                        .with(csrf()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                "id": "123456",
                                "kaffeesorteName": "KaffeesorteA",
                                "roestereiName": "RoestereiA",
                                "aufbereitung": "AufbereitungA",
                                "herkunftsland": "LandA",
                                "aromen": "AromenA",
                                "aromenProfil": "AromenprofilA",
                                "koerper": "KoerperA",
                                "suesse": "SuesseA",
                                "geschmacksnotenHeiss": "GeschmacksnotenHeissA",
                                "geschmacksnotenMedium": "GeschmacksnotenMediumA",
                                "geschmacksnotenKalt": "GeschmacksnotenKaltA",
                                "freezingDate": "FreezingDateA",
                                "fotoUrlKaffeesorte": "FotoUrlKaffeesorteA"
                                }
                        """
                ));
    }

    @Test
    @WithMockUser(username = "Dan", password = "123")
    void deleteKaffeesorte_shouldReturn404_whenNoKaffeesorteWasFoundToDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/barista/kaffeesorte/1000")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "Dan", password = "123")
    void updateKaffeesorte_shouldReturnUpdatedKaffeesorte_whenAromaProfilWasChanged() throws Exception {
        Kaffeesorte originalKaffeesorte = new Kaffeesorte("123456","KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        kaffeesorteRepo.save(originalKaffeesorte);

        mvc.perform(MockMvcRequestBuilders.put("/barista/kaffeesorte/123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                 {
                                     "id": "123456",
                                     "kaffeesorteName": "KaffeesorteA",
                                     "roestereiName": "RoestereiA",
                                     "variety": "VarietyA",
                                     "aufbereitung": "AufbereitungA",
                                     "herkunftsland": "LandA",
                                     "aromen": "AromenA",
                                     "aromenProfil": "UpdatedAromenProfil",
                                     "koerper": "KoerperA",
                                     "suesse": "SuesseA",
                                     "geschmacksnotenHeiss": "GeschmacksnotenHeissA",
                                     "geschmacksnotenMedium": "GeschmacksnotenMediumA",
                                     "geschmacksnotenKalt": "GeschmacksnotenKaltA",
                                     "freezingDate": "FreezingDateA",
                                     "fotoUrlKaffeesorte": "FotoUrlKaffeesorteA"
                                 }
                                """
                        )
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                         {
                             "id": "123456",
                             "kaffeesorteName": "KaffeesorteA",
                             "roestereiName": "RoestereiA",
                             "variety": "VarietyA",
                             "aufbereitung": "AufbereitungA",
                             "herkunftsland": "LandA",
                             "aromen": "AromenA",
                             "aromenProfil": "UpdatedAromenProfil",
                             "koerper": "KoerperA",
                             "suesse": "SuesseA",
                             "geschmacksnotenHeiss": "GeschmacksnotenHeissA",
                             "geschmacksnotenMedium": "GeschmacksnotenMediumA",
                             "geschmacksnotenKalt": "GeschmacksnotenKaltA",
                             "freezingDate": "FreezingDateA",
                             "fotoUrlKaffeesorte": "FotoUrlKaffeesorteA"
                         }
                        """
                ));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Dan", password = "123")
    void getFilteredKaffeesorten_shouldReturnFilteredKaffeesorten_basedOnParams() throws Exception {
        Kaffeesorte kaffeesorte1 = new Kaffeesorte("123", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA, blumig",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        Kaffeesorte kaffeesorte2 = new Kaffeesorte("456", "KaffeesorteB", "RoestereiB", "VarietyB",
                "AufbereitungB", "LandB", "AromenB", "AromenprofilB, fruchtig",
                "KoerperB", "SuesseB", "GeschmacksnotenHeissB", "GeschmacksnotenMediumB",
                "GeschmacksnotenKaltB", "FreezingDateB", "FotoUrlKaffeesorteB");

        kaffeesorteRepo.save(kaffeesorte1);
        kaffeesorteRepo.save(kaffeesorte2);

        mvc.perform(MockMvcRequestBuilders.get("/barista/kaffeesorten/filter")
                        .param("roestereiName", "RoestereiA")
                        .param("aromenProfil", "blumig")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.kaffeesorten", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kaffeesorten[0].roestereiName", Matchers.is("RoestereiA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kaffeesorten[0].aromenProfil", Matchers.containsString("blumig")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems", Matchers.is(1)));
    }


    //Tasting

    @DirtiesContext
    @Test
    @WithMockUser(username = "Dan", password = "123")
    void filterTastings_shouldReturnFilteredTastings_basedOnParams() throws Exception {
        Tasting tasting1 = new Tasting("123", "TastingA", "KaffeesorteA", "ZubereitungA", "MahlgradA",
                "WassersorteA", "WassertemperaturA", "ErgebnisA", 5, "AnmerkungenA");
        Tasting tasting2 = new Tasting("456", "TastingB", "KaffeesorteB", "ZubereitungB", "MahlgradB",
                "WassersorteB", "WassertemperaturB", "ErgebnisB", 1, "AnmerkungenB");

        tastingRepo.save(tasting1);
        tastingRepo.save(tasting2);

        mvc.perform(MockMvcRequestBuilders.get("/barista/tastings/filter")
                        .param("zubereitungsmethodeName", "ZubereitungA")
                        .param("bewertungFrontend", "mittel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tastingName", Matchers.is("TastingA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].zubereitungsmethodeName", Matchers.is("ZubereitungA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bewertung", Matchers.is(5)));
    }

    @Test
    void getKaffeesorteById_shouldReturnKaffeesorte() throws Exception {
        Kaffeesorte kaffeesorte = new Kaffeesorte("123", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA, blumig",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        kaffeesorteRepo.save(kaffeesorte);

        mvc.perform(MockMvcRequestBuilders.get("/barista/kaffeesorte/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kaffeesorteName", Matchers.is("KaffeesorteA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roestereiName", Matchers.is("RoestereiA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.variety", Matchers.is("VarietyA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aufbereitung", Matchers.is("AufbereitungA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.herkunftsland", Matchers.is("LandA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aromen", Matchers.is("AromenA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aromenProfil", Matchers.is("AromenprofilA, blumig")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.koerper", Matchers.is("KoerperA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.suesse", Matchers.is("SuesseA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.geschmacksnotenHeiss", Matchers.is("GeschmacksnotenHeissA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.geschmacksnotenMedium", Matchers.is("GeschmacksnotenMediumA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.geschmacksnotenKalt", Matchers.is("GeschmacksnotenKaltA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.freezingDate", Matchers.is("FreezingDateA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fotoUrlKaffeesorte", Matchers.is("FotoUrlKaffeesorteA")));
    }




}








