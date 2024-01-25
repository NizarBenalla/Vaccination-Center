package com.example.clientvacination.services;

import com.example.clientvacination.dao.centreVaccinationRepository;
import com.example.clientvacination.dao.citoyenRepository;
import com.example.clientvacination.entities.CentreVaccination;
import com.example.clientvacination.entities.Citoyen;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@Transactional

public class citoyenServiceImpl implements ICitoyenService{
    @Autowired
    private citoyenRepository citoyenRepository;
    @Autowired
    private centreVaccinationRepository centreVaccinationRepository;

    public void ajouterCitoyen(Citoyen citoyen) {
        if (citoyen != null) {
            // Your logic to save the Citoyen in the database
            citoyenRepository.saveAndFlush(citoyen);
        } else {
            // Handle the case where citoyen is null
            // You can throw an exception, log an error, or handle it in any way you prefer
            throw new IllegalArgumentException("Citoyen cannot be null");
        }
    }

    public void ajouterCentreVaccination(CentreVaccination centreVaccination) {
        if (centreVaccination != null) {
            // Your logic to save the CentreVaccination in the database
            centreVaccinationRepository.saveAndFlush(centreVaccination);
        } else {
            // Handle the case where centreVaccination is null
            // You can throw an exception, log an error, or handle it in any way you prefer
            throw new IllegalArgumentException("CentreVaccination cannot be null");
        }
    }
    public Collection<Citoyen> getCitizensByCentre(CentreVaccination centreVaccination) {
        return citoyenRepository.findByCentreVaccination(centreVaccination);
    }


    public Collection<Citoyen> afficherCitoyens() {
        return citoyenRepository.findAll();
    }

    public Collection<CentreVaccination> afficherCentres(){
        return centreVaccinationRepository.findAll();

    }
    public void addCitizenToCentre(Long centreId, String citizenName) {
        // Logic to add the citizen to the specified center
        CentreVaccination centre = centreVaccinationRepository.findById(centreId).orElse(null);

        if (centre != null) {
            Citoyen citizen = new Citoyen();
            citizen.setNom(citizenName);
            citizen.setCentreVaccination(centre);
            citoyenRepository.save(citizen);
        }
}

    public Page<CentreVaccination> getPaginatedCentres(int page, int size, String keyword) {
        return centreVaccinationRepository.findByNomContains(keyword, PageRequest.of(page, size));
    }
}
