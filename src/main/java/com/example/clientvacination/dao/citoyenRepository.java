package com.example.clientvacination.dao;

import com.example.clientvacination.entities.CentreVaccination;
import com.example.clientvacination.entities.Citoyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.Optional;

@RepositoryRestResource
public interface citoyenRepository extends JpaRepository<Citoyen,Long> {

    Collection<Citoyen> findByCentreVaccination(CentreVaccination centreVaccination);
}
