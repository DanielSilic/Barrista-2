package com.example.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roesterei {

    private String RoestereiName;
    private String RoestereiBeschreibung;
    private String FotoUrlRoesterei;

}
