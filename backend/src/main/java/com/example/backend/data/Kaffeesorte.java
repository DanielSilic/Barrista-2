package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kaffeesorte {

    @Id
    private String Id;
    private String KaffeesorteName;
    private String RoestereiName;
    private String Variety;
    private String Aufbereitung;
    private String Herkunftsland;
    private String Aromen;
    private String Aromenprofil;
    private String Koerper;
    private String Suesse;
    private String GeschmacksnotenHeiss;
    private String GeschmacksnotenMedium;
    private String GeschmacksnotenKalt;
    private String FreezingDate;
    private String FotoUrlKaffeesorte;
}
