package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roesterei {

    @Id
    private String id;
    private String roestereiName;
    private String roestereiBeschreibung;
    private String fotoUrlRoesterei;

}
