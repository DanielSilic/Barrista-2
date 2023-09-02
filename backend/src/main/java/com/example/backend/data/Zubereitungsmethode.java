package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zubereitungsmethode {
    @Id
    private String Id;
    private String ZubereitungsmethodeName;
    private String Barrista;
    private String MethodenType;
    private String MethodenBeschreibung;
    private String FotoUrlZubereitungsmethode;
}
