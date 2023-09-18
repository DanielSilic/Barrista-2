package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Tasting")
@NoArgsConstructor
@AllArgsConstructor
public class Tasting {

    private String id;
    private String tastingName;
    private String kaffeesorteName;
    private String zubereitungsmethodeName;
    private String mahlgrad;
    private String wassersorte;
    private String wassertemperatur;
    private String ergebnis;
    private int bewertung;
    private String anmerkungen;
}
