package com.example.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tasting {

    private String Id;
    private String TastingName;
    private String KaffeeSorteName;
    private String ZubereitungsmethodeName;
    private String Mahlgrad;
    private String WasserSorte;
    private String Wassertemperatur;
    private String Ergebnis;
    private int Bewertung;
    private String Anmerkungen;

}
