package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.repos.KaffeesorteRepo;
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

        mvc.perform(MockMvcRequestBuilders.delete("/barista/newkaffeesorte/123456")
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

}
