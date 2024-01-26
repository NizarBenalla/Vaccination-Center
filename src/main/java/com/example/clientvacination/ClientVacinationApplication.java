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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class ClientVacinationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientVacinationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(citoyenRepository citoyenRepository, centreVaccinationRepository centreVaccinationRepository) {

        return args -> {
            List<String> centreNamesList = Arrays.asList("Centre_Vaccination_Rabat", "Centre_Vaccination_Casablanca", "Centre_Vaccination_Marrakech", "Centre_Vaccination_Fes", "Centre_Vaccination_Tangier");
            List<String> addressesList = Arrays.asList("Rue Principale, Rabat", "Avenue Hassan II, Casablanca", "Avenue Mohammed V, Marrakech", "Avenue Moulay Youssef, Fes", "Avenue Mohammed VI, Tangier");

            Map<String, String> centreAddressesMap = IntStream.range(0, Math.min(centreNamesList.size(), addressesList.size()))
                    .boxed().collect(Collectors.toMap(
                            i -> centreNamesList.get(i),
                            i -> addressesList.get(i),
                            (existing, replacement) -> replacement,
                            LinkedHashMap::new
                    ));

            centreAddressesMap.forEach((centre, address) -> {
                CentreVaccination centreVaccination = new CentreVaccination();
                centreVaccination.setNom(centre);
                centreVaccination.setAdresse(address);
                centreVaccinationRepository.save(centreVaccination);
            });

            List<String> namesList = Arrays.asList("Amina", "Youssef", "Leila", "Omar", "Zineb", "Karim", "Sara", "Mehdi");

            Map<String, String> clientCentreMap = IntStream.range(0, Math.min(namesList.size(), centreNamesList.size()))
                    .boxed().collect(Collectors.toMap(
                            i -> namesList.get(i),
                            i -> centreNamesList.get(i),
                            (existing, replacement) -> replacement,
                            LinkedHashMap::new
                    ));

            clientCentreMap.forEach((client, centre) -> {
                if (client != null && centre != null) {
                    Citoyen citoyen = new Citoyen();
                    citoyen.setNom(client);

                    CentreVaccination centreVaccination = centreVaccinationRepository.findByNom(centre).stream().findFirst().orElse(null);

                    if (centreVaccination == null) {
                        throw new EntityNotFoundException("CentreVaccination not found for name: " + centre);
                    }

                    citoyen.setCentreVaccination(centreVaccination);

                    String centreName = centreVaccination.getNom();
                    // citoyenRepository.save(citoyen);
                }
            });
        };
    }
}
