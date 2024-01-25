package com.example.clientvacination.services;

import com.example.clientvacination.entities.CentreVaccination;
import com.example.clientvacination.entities.Citoyen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ICitoyenService {
    public void ajouterCitoyen(Citoyen citoyen) ;
    public void ajouterCentreVaccination(CentreVaccination centreVaccination);
    public Collection<Citoyen> afficherCitoyens();
    public void addCitizenToCentre(Long centreId, String citizenName);

}
