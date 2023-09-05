package com.example.backend;

import com.example.backend.data.Kaffeesorte;
import com.example.backend.repos.KaffeesorteRepo;
import com.example.backend.repos.RoestereiRepo;
import com.example.backend.repos.TastingRepo;
import com.example.backend.repos.ZubereitungsmethodeRepo;
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
    private RoestereiRepo roestereiRepo;

    @Autowired
    private TastingRepo tastingRepo;

    @Autowired
    private ZubereitungsmethodeRepo zubereitungsmethodeRepo;

    @Test
    void getAllKaffeesorten_shouldReturnEmptyList_whenRepositoryIsEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/kaffeesorten"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @DirtiesContext
    @Test
    void getAllKaffeesorten_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        Kaffeesorte kaffeesorte = new Kaffeesorte("123456", "KaffeesorteA", "RoestereiA", "VarietyA",
                "AufbereitungA", "LandA", "AromenA", "AromenprofilA",
                "KoerperA", "SuesseA", "GeschmacksnotenHeissA", "GeschmacksnotenMediumA",
                "GeschmacksnotenKaltA", "FreezingDateA", "FotoUrlKaffeesorteA");

        kaffeesorteRepo.save(kaffeesorte);

        mvc.perform(MockMvcRequestBuilders.get("/api/kaffeesorten"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        [
                           {
                             "id": "123456",
                             "KaffeesorteName": "KaffeesorteA",
                             "RoestereiName": "RoestereiA",
                             "Aufbereitung": "AufbereitungA",
                             "Land": "LandA",
                             "Aromen": "AromenA",
                             "Aromenprofil": "AromenpfilA",
                             "Koerper": "KoerperA",
                             "Suesse": "SuesseA",
                             "GeschmacksnotenHeiss": "GeschmacksnotenHeissA",
                             "GeschmacksnotenMedium": "GeschmacksnotenMediumA",
                             "GeschmacksnotenKalt": "GeschmacksnotenHaltA",
                             "FreezingDate": "FreezingDateA",
                             "FotoUrlKaffeesorte": "FotoUrlKaffeesorteA"
                           }
                         ]
                        """
                ));
    }


    @DirtiesContext
    @Test
    @WithMockUser(username = "Dan", password = "123")
    void addKaffeesorte_shouldSaveAndReturnKaffeesorte_whenKaffeesorteDoesNotExistInRepo() throws Exception {
        String kaffeesorteJson = """
        {
            "id": "123456",
            "KaffeesorteName": "KaffeesorteA",
            "RoestereiName": "RoestereiA",
            "Aufbereitung": "AufbereitungA",
            "Land": "LandA",
            "Aromen": "AromenA",
            "Aromenprofil": "AromenpfilA",
            "Koerper": "KoerperA",
            "Suesse": "SuesseA",
            "GeschmacksnotenHeiss": "GeschmacksnotenHeissA",
            "GeschmacksnotenMedium": "GeschmacksnotenMediumA",
            "GeschmacksnotenKalt": "GeschmacksnotenHaltA",
            "FreezingDate": "FreezingDateA",
            "FotoUrlKaffeesorte": "FotoUrlKaffeesorteA"
        }
    """;

        mvc.perform(MockMvcRequestBuilders.post("/api/kaffeesorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(kaffeesorteJson)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(kaffeesorteJson));
    }








}
