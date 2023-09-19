package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Zubereitungsmethode")
@NoArgsConstructor
@AllArgsConstructor
public class Zubereitungsmethode {

    private String id;
    private String zubereitungsmethodeName;
    private String barista;
    private String methodenType;
    private String methodenBeschreibung;
    private String fotoUrlZubereitungsmethode;
}
