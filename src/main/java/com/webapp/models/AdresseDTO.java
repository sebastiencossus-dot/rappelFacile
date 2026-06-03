package com.webapp.models;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdresseDTO {

    private String rue;
    private String numero;
    private String ville;
    private String codePostal;
}