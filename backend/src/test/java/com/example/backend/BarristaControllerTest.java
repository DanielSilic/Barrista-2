package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.data.Tasting;
import com.example.backend.repos.KaffeesorteRepo;
import com.example.backend.repos.TastingRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
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
public class BarristaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private KaffeesorteRepo kaffeesorteRepo;

    @Autowired
    private TastingRepo tastingRepo;


    /*Kaffeesorte: getAll_EmptyList, getAll_OneObject, addDuplicate409,
    deleteKaffeesorte, deleteKaffeesorte_404, UpdateKaffeesorte, getFilteredKaffeesorten*/
    @DirtiesContext
    @Test
    @WithMockUser(username = "Dan", password = "123")
    void getAllKaffeesorten_shouldReturnEmptyList_whenDatabaseIsEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/barista/kaffeesorten")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "Dan", password = "123")
    void getAllKaffeesorten_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        Kaffeesorte kaffeesorte = new Kaffeesorte("123456","KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        kaffeesorteRepo.save(kaffeesorte);

        mvc.perform(MockMvcRequestBuilders.get("/barista/kaffeesorten"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        [
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
                         ]
                        """
                ));
    }

    @Test
    @WithMockUser(username = "Dan", password = "123")
    @DirtiesContext
    void addDuplicateKaffeesorte_ShouldReturn409_CheckWithNAmeAndRoesterei() throws Exception {
        Kaffeesorte originalKaffeesorte = new Kaffeesorte("123456","KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        kaffeesorteRepo.save(originalKaffeesorte);

        String duplicateKaffeesorteJson = """
        {
            "id": "123457",
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
    """;

        mvc.perform(MockMvcRequestBuilders.post("/barista/newkaffeesorte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateKaffeesorteJson)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
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
    void deleteEmployee_shouldReturn404_whenNoEmplyoeeWasFoundToDelete() throws Exception {
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

    @DirtiesContext
    @Test
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
                        .param("RoestereiName", "RoestereiA")
                        .param("aromenProfil", "blumig")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].roestereiName", Matchers.is("RoestereiA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].aromenProfil", Matchers.containsString("blumig")));
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

}








