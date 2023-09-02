package com.example.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roesterei {

    @Id
    private String Id;
    private String RoestereiName;
    private String RoestereiBeschreibung;
    private String FotoUrlRoesterei;

}
