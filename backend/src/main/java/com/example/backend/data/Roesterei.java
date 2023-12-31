package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Roesterei")
@NoArgsConstructor
@AllArgsConstructor
public class Roesterei {


    private String id;
    private String roestereiName;
    private String roestereiBeschreibung;
    private String fotoUrlRoesterei;

}
