package com.iway_tn.doctor_controller.controller;

import com.iway_tn.doctor_controller.model.Dossier;
import com.iway_tn.doctor_controller.model.Enum.Status;
import com.iway_tn.doctor_controller.service.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
@RequestMapping("/dossiers")
public class DossierController {

    @Autowired
    private DossierService dossierService;
    @PostMapping("/add")
    public ResponseEntity<Dossier> createDossier(MultiValueMap<String, String> formData) {
        Dossier dossier = new Dossier();

        dossier.setCreatedDate(formData.getFirst("createdDate"));
        dossier.setPatientName(formData.getFirst("patientName"));
        dossier.setStatus(Status.valueOf(formData.getFirst("status")));
        dossier.setCompletedDate(formData.getFirst("completedDate"));

        return ResponseEntity.status(HttpStatus.CREATED).body(dossierService.addDossier(dossier));
    }

    @GetMapping
    public String showCompletedDossiers(Model model) {
        model.addAttribute("contacts", dossierService.getAllCompletedDossiers());
        return "redirect:/list-dossier.html";
    }

    @PostMapping("/delete")
    public String deleteContact(@RequestParam("id_dossier") long id_dossier, RedirectAttributes redirectAttributes) {
        Optional<Dossier> dossier = Optional.ofNullable(dossierService.getDossierById(id_dossier));
        if (dossier.isPresent()) {
            dossierService.deleteDossierById(id_dossier);
            redirectAttributes.addFlashAttribute("message", "Contact deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Contact not found!");
        }
        return "redirect:/contacts";
    }
}
