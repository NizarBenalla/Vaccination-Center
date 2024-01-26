package com.example.clientvacination.controllers;

import com.example.clientvacination.dao.centreVaccinationRepository;
import com.example.clientvacination.entities.CentreVaccination;
import com.example.clientvacination.entities.Citoyen;
import com.example.clientvacination.services.citoyenServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
@Controller
public class citoyenController {
    @Controller
    public class CitoyenController {
        @Autowired
        private citoyenServiceImpl citoyenService;
        @Autowired
        private centreVaccinationRepository centreVaccinationRepository;



     /*   @GetMapping("/T")
        public String afficherCitoyens( Model model) {
            Collection<Citoyen> citoyens = citoyenService.afficherCitoyens();
            model.addAttribute("citoyens", citoyens);
            return "viewt";
        }*/
     @GetMapping("/")
     public String afficherCentres(Model model,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "5") int size,
                                   @RequestParam(name = "keyword", defaultValue = "") String keyword) {
         Page<CentreVaccination> pageCentres;

         if (keyword.isEmpty()) {
             // If no keyword is provided, show all centers
             pageCentres = centreVaccinationRepository.findAll(PageRequest.of(page, size));
         } else {
             // If a keyword is provided, search by name containing the keyword
             pageCentres = centreVaccinationRepository.findByNomContains(keyword, PageRequest.of(page, size));
         }

         model.addAttribute("centres", pageCentres.getContent());
         model.addAttribute("pages", new int[pageCentres.getTotalPages()]);
         model.addAttribute("currentPage", page);
         model.addAttribute("keyword", keyword);
         int startIndex = page * size + 1;
         int endIndex = (int) Math.min(startIndex + size - 1, pageCentres.getTotalElements());
         int totalItems = (int) pageCentres.getTotalElements();

         model.addAttribute("startIndex", startIndex);
         model.addAttribute("endIndex", endIndex);
         model.addAttribute("totalItems", totalItems);

         return "Index";
     }


        @GetMapping("/addCitoyen")
        public String showAddCitoyenForm(Model model, @RequestParam Long centreId) {
            CentreVaccination selectedCentre = centreVaccinationRepository.findById(centreId).orElseThrow(() -> new IllegalArgumentException("Centre not found"));
            model.addAttribute("selectedCentre", selectedCentre);
            return "AddCitizens";
        }


        @PostMapping("/addCitoyen")
        public String addCitizen(@RequestParam Long centreId, @RequestParam String citizenName) {
            // Logic to add the citizen to the specified center
            citoyenService.addCitizenToCentre(centreId, citizenName);
            return "AddCitizens"; // Rediriger vers la page précédente
        }

        @PostMapping("/saveCitoyen")
        public String saveCitoyen(@RequestParam String nom, @RequestParam Long centreVaccinationId) {
            CentreVaccination centreVaccination = centreVaccinationRepository.findById(centreVaccinationId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid CentreVaccination Id: " + centreVaccinationId));

            Citoyen citoyen = new Citoyen();
            citoyen.setNom(nom);
            citoyen.setCentreVaccination(centreVaccination);

            citoyenService.ajouterCitoyen(citoyen);

            // Redirect to the URL with the correct parameter name 'id'
            return "redirect:/citoyens/centre?id=" + centreVaccination.getId();
        }



        @GetMapping("/addVaccinationCenter")
        public String showAddCentreVaccinationForm() {
            return "addingVaccinationCenter";
        }

        @PostMapping("/saveVaccinationCentre")
        public String saveCentreVaccination(@RequestParam String nom, @RequestParam String adresse) {
            CentreVaccination centreVaccination = new CentreVaccination();
            centreVaccination.setNom(nom);
            centreVaccination.setAdresse(adresse);

            citoyenService.ajouterCentreVaccination(centreVaccination);
            return "redirect:/";
        }

        @GetMapping("/citoyens/centre")
        public String viewCitizensByCentre(@RequestParam Long id, @NotNull Model model) {
            CentreVaccination centreVaccination = centreVaccinationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid CentreVaccination ID: " + id));

            Collection<Citoyen> citizensByCentre = citoyenService.getCitizensByCentre(centreVaccination);

            model.addAttribute("centreVaccination", centreVaccination);
            model.addAttribute("citizensByCentre", citizensByCentre);

            return "citizensByVaxCenter";
        }



        @PostMapping("/delete")
        public String delete(@RequestParam Long id){
            centreVaccinationRepository.deleteById(id);
            return "redirect:/";
        }
           /*  @PostMapping("/saveCitoyen")
        public String saveCitoyen(@RequestParam String nom, @RequestParam Long centreVaccinationId) {
            CentreVaccination centreVaccination = centreVaccinationRepository.findById(centreVaccinationId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid CentreVaccination Id: " + centreVaccinationId));

            Citoyen citoyen = new Citoyen();
            citoyen.setNom(nom);
            citoyen.setCentreVaccination(centreVaccination);

            citoyenService.ajouterCitoyen(citoyen);
            return "redirect:/";
        }*/


    }
}
