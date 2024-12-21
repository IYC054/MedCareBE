package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Doctorworking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorWorkingHourRepository extends JpaRepository<Doctorworking, Integer> {
    List<Doctorworking> findByDoctor_Id(int doctorId); // Truy vấn bằng ID của Doctor

}
