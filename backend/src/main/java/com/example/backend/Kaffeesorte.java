package com.example.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kaffeesorte {

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
