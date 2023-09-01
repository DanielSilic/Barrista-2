package com.example.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zubereitungsmethode {

    private String Id;
    private String ZubereitungsmethodeName;
    private String Barrista;
    private String MethodenType;
    private String MethodenBeschreibung;
    private String FotoUrlZubereitungsmethode;
}
