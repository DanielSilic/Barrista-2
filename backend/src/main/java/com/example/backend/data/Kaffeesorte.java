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
    private String id;
    private String kaffeesorteName;
    private String roestereiName;
    private String variety;
    private String aufbereitung;
    private String herkunftsland;
    private String aromen;
    private String aromenProfil;
    private String koerper;
    private String suesse;
    private String geschmacksnotenHeiss;
    private String geschmacksnotenMedium;
    private String geschmacksnotenKalt;
    private String freezingDate;
    private String fotoUrlKaffeesorte;
}
