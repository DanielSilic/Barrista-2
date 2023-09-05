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
    private String id;
    private String zubereitungsmethodeName;
    private String barista;
    private String methodenType;
    private String methodenBeschreibung;
    private String fotoUrlZubereitungsmethode;
}
