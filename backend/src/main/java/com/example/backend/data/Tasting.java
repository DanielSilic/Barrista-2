package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tasting {
    @Id
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
