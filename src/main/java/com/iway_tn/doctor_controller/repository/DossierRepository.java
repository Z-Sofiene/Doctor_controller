package com.iway_tn.doctor_controller.repository;

import com.iway_tn.doctor_controller.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierRepository extends JpaRepository<Dossier,Long> {
    @Query("select d from Dossier d where upper(d.status) = 'COMPLETED'")
    List<Dossier> findByCompletedStatus();

    @Query("select d from Dossier d where d.patientName = ?1")
    Dossier findByPatientName(String patientName);

}
