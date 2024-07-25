package com.iway_tn.doctor_controller.ServiceImp;

import com.iway_tn.doctor_controller.model.Dossier;
import com.iway_tn.doctor_controller.repository.DossierRepository;
import com.iway_tn.doctor_controller.service.DossierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DossierServiceImp implements DossierService {

    @Autowired
    private DossierRepository repositoryDossier;
    @Override
    public Dossier getDossierById(long id_dossier) {
        return repositoryDossier.findById(id_dossier).orElse(null);
    }

    @Override
    public Dossier updateDossier(Dossier dossier) {
        return repositoryDossier.save(dossier);
    }

    @Override
    public void deleteDossierById(long id_dossier) {
        repositoryDossier.deleteById(id_dossier);
    }

    @Override
    public Dossier addDossier(Dossier dossier) {
        return repositoryDossier.save(dossier);
    }

    @Override
    public List<Dossier> getAllCompletedDossiers() {
        return repositoryDossier.findByCompletedStatus();
    }

    @Override
    public List<Dossier> getAllDossiers() {
        return repositoryDossier.findAll();
    }

    @Override
    public Dossier getDossierByPatientName(String patientName) {
        return repositoryDossier.findByPatientName(patientName);
    }
}
