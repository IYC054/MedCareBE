package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Doctorworking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorWorkingHourRepository extends JpaRepository<Doctorworking, Integer> {
    @Query("SELECT dwh FROM Doctorworking dwh WHERE dwh.doctor.id = :doctorId")
    List<Doctorworking> findByDoctorId(@Param("doctorId") int doctorId);

    // Xóa tất cả các bản ghi có doctor_id = id
    @Modifying
    @Transactional
    @Query("DELETE FROM Doctorworking dwh WHERE dwh.doctor.id = :doctorId")
    void deleteByDoctorId(@Param("doctorId") int doctorId);



}
