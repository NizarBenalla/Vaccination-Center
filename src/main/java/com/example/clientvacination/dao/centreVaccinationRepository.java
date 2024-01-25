package com.example.clientvacination.dao;

import com.example.clientvacination.entities.CentreVaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RepositoryRestResource
public interface centreVaccinationRepository extends JpaRepository<CentreVaccination,Long> {
    Page<CentreVaccination> findByNomContains(String kw, Pageable pageable);

    List<CentreVaccination> findByNom(String nom);
}
