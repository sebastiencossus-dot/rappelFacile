package com.webapp.models;



import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RDV {


    private Integer id;
    private LocalDate dateRdv;
    private Integer isOK;

    private Prestataires prestataires;
    private Professions professions;


}