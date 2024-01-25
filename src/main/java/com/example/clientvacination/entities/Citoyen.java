package com.example.clientvacination.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Citoyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "centre_vaccination_id")  // Add this annotation to specify the join column
    private CentreVaccination centreVaccination;

    // Transient method to directly get the name of the vaccination center
    @Transient
    public String getCentreVaccinationName() {
        return centreVaccination != null ? centreVaccination.getNom() : null;
    }

    // Transient method to directly set the name of the vaccination center
    @Transient
    public void setCentreVaccinationName(String centreVaccinationName) {
        if (centreVaccination != null) {
            centreVaccination.setNom(centreVaccinationName);
        }
    }
}
