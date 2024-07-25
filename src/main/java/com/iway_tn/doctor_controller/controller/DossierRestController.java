package com.iway_tn.doctor_controller.controller;

import com.iway_tn.doctor_controller.model.Dossier;
import com.iway_tn.doctor_controller.model.Enum.Status;
import com.iway_tn.doctor_controller.service.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dossiers")
public class DossierRestController {

    @Autowired
    private DossierService dossierService;

    @GetMapping("/{id}")
    public ResponseEntity<Dossier> getDossierById(@PathVariable Long id) {
        Optional<Dossier> Dossier = Optional.ofNullable(dossierService.getDossierById(id));
        return Dossier.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<Dossier>> listDossiers() {
        List<Dossier> Dossiers = dossierService.getAllCompletedDossiers();
        return new ResponseEntity<>(Dossiers, HttpStatus.OK);
    }

    @PostMapping
    public RedirectView createDossier(@RequestParam("patientName") String patientName,
                                         @RequestParam("status") String status,
                                         @RequestParam("createdDate") String createdDate,
                                         @RequestParam("completedDate") String completedDate,
                                         RedirectAttributes redirectAttributes) {
        Dossier d = new Dossier();
        d.setPatientName(patientName);
        d.setCreatedDate(createdDate);
        d.setCompletedDate(completedDate);
        d.setStatus(Status.valueOf(status));

        dossierService.addDossier(d);

        redirectAttributes.addFlashAttribute("message", "Dossier added successfully!");

        return new RedirectView("/");
    }

    @PutMapping("/{id_dossier}")
    public ResponseEntity<Dossier> updateDossier(@PathVariable long id_dossier, @RequestBody Dossier dossierDetails) {
        Optional<Dossier> dossier = Optional.ofNullable(dossierService.getDossierById(id_dossier));
        if (dossier.isPresent()) {
            Dossier updatedDossier = dossier.get();
            updatedDossier.setStatus(dossierDetails.getStatus());
            updatedDossier.setCompletedDate(dossierDetails.getCompletedDate());
            return ResponseEntity.ok(dossierService.addDossier(updatedDossier));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_dossier}")
    public ResponseEntity<Void> deleteDossier(@PathVariable long id_dossier) {
        Optional<Dossier> Dossier = Optional.ofNullable(dossierService.getDossierById(id_dossier));
        if (Dossier.isPresent()) {
            dossierService.deleteDossierById(id_dossier);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}