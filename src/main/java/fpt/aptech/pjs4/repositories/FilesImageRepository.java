package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.FilesImage;
import fpt.aptech.pjs4.entities.PatientFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilesImageRepository extends JpaRepository<FilesImage, Integer> {
    @Query("SELECT p FROM FilesImage p WHERE p.patientsFiles.id = :patientfileId")
    List<FilesImage> findFileImageByPatientFileId(@Param("patientfileId") int id);
}
