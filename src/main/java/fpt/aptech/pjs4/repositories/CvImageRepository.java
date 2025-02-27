package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.CvImage;
import fpt.aptech.pjs4.entities.FilesImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CvImageRepository extends JpaRepository<CvImage, Integer> {
    @Query("SELECT p FROM CvImage p WHERE p.doctorFile.id = :doctorId")
    List<CvImage> findFileImageByCvImageId(@Param("doctorId") int id);
}
