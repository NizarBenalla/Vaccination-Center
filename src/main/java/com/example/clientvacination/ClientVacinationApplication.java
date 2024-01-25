package com.example.clientvacination;
import com.example.clientvacination.dao.centreVaccinationRepository;
import com.example.clientvacination.dao.citoyenRepository;


import com.example.clientvacination.entities.CentreVaccination;
import com.example.clientvacination.entities.Citoyen;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
public class ClientVacinationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientVacinationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(citoyenRepository citoyenRepository, centreVaccinationRepository centreVaccinationRepository) {


        return args -> {

            // Stream of Centres names
            // List of centres adresses
            List<String> lycéeNamesList = Arrays.asList("Lycée Kandy", "Lycée abi hayan lycee taouhidi", "Lycée bnou alyassamine", "Lycée taha houssein", "Lycée bnou el arabi");

            // List of addresses
            List<String> addressesList = Arrays.asList("Sbata (arrondissement)", "Bouskoura(mairie)",
                    "Hay hassani\n(arrondissement)", "El fida (arrondissement)", "Ain chock (arrondissement)");

            Map<String, String> lycéeAddressesMap = IntStream.range(0, Math.min(lycéeNamesList.size(), addressesList.size()))
                    .boxed().collect(Collectors.toMap(
                            i -> lycéeNamesList.get(i),
                            i -> addressesList.get(i),
                            (existing, replacement) -> replacement, // Handle duplicates if any
                            LinkedHashMap::new // Preserve the order of insertion
                    ));

            // Save lycée names and associated addresses to the database
            lycéeAddressesMap.forEach((lycée, address) -> {
                CentreVaccination centreVaccination = new CentreVaccination();
                centreVaccination.setNom(lycée);
                centreVaccination.setAdresse(address);
                centreVaccinationRepository.save(centreVaccination);
            });

          /*  Stream.of("Sbata (arrondissement)","Bouskoura(mairie)","Hay hassani\\n\" +\n" +
                    "                            \"(arrondissement)","El fida (arrondissement)","Ain chock (arrondissement)");
            Stream.of("Lycée (Kandy)", "Lycée abi hayan" +
                            "lycee taouhidi", "Lycée bnou" +
                            "alyassamine","Lycée taha houssein","Lycée bnou el arabi")
                    .forEach(name -> {
                        CentreVaccination centreVaccination = new CentreVaccination();
                        centreVaccination.setNom(name);
                        centreVaccinationRepository.save(centreVaccination);
                    });
            Stream.of("Fati", "Halima", "Yassin","Haytam","Asmae","Chaimae","Jade","Hiba")
                    .forEach(name -> {
                        Citoyen citoyen = new Citoyen();
                        citoyen.setNom(name);
                        citoyenRepository.save(citoyen);
                    });
*/
            List<String> namesList = Arrays.asList("Fati", "Halima", "Yassin", "Haytam", "Asmae", "Chaimae", "Jade", "Hiba");

// Create a mapping between names and centers
            Map<String, String> clientCentreMap = IntStream.range(0, Math.min(namesList.size(), lycéeNamesList.size()))
                    .boxed().collect(Collectors.toMap(
                            i -> namesList.get(i),
                            i -> lycéeNamesList.get(i),
                            (existing, replacement) -> replacement, // Handle duplicates if any
                            LinkedHashMap::new // Preserve the order of insertion
                    ));

// For each client, create a new Citoyen entity and associate it with the vaccine center's name
            clientCentreMap.forEach((client, centre) -> {
                if (client != null && centre != null) {
                    Citoyen citoyen = new Citoyen();
                    citoyen.setNom(client);

                    // Utilisez .stream().findFirst().orElse(null) pour obtenir le premier élément ou null
                    CentreVaccination centreVaccination = centreVaccinationRepository.findByNom(centre).stream().findFirst().orElse(null);

                    if (centreVaccination == null) {
                        // Handle the case where the CentreVaccination is not found
                        // You may log a warning or throw an exception depending on your requirements
                        throw new EntityNotFoundException("CentreVaccination not found for name: " + centre);
                    }

                    citoyen.setCentreVaccination(centreVaccination);

                    // Set the name of the CentreVaccination
                    String centreName = centreVaccination.getNom();
                 //   citoyenRepository.save(citoyen);
                }
            });




        };

    }
}