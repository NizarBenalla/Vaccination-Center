package com.example.clientvacination.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class CentreVaccination {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    @OneToMany (mappedBy = "centreVaccination")
    private Collection<Citoyen> citoyens;
}
