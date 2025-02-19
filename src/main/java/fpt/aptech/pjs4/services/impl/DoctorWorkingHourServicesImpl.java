package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.DoctorworkingDTO;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.entities.Doctorworking;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.repositories.DoctorRepository;
import fpt.aptech.pjs4.repositories.DoctorWorkingHourRepository;
import fpt.aptech.pjs4.services.DoctorWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorWorkingHourServicesImpl implements DoctorWorkingHourService {

    @Autowired
    private DoctorWorkingHourRepository doctorWorkingHourRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctorworking> getAllWorkingHours() {
        return doctorWorkingHourRepository.findAll();
    }

    @Override
    public Doctorworking getWorkingHour(int id) {
        Optional<Doctorworking> workingHour = doctorWorkingHourRepository.findById(id);
        return workingHour.orElse(null); // Trả về null nếu không tìm thấy
    }

    @Override
    public List<Doctorworking> addWorkingHour(DoctorworkingDTO workingHour) {
        try {
            LocalDate today = workingHour.getWorkStart();
            // Assume workingHour.getWorkDate() returns a LocalDate object
            LocalDate workDate = workingHour.getWorkDate();
            // Create a list to store the working hours for each date between today and workDate
            List<Doctorworking> workingHoursList = new ArrayList<>();
            // Fetch the Doctor object from the repository using the doctor ID
            Integer doctorId = workingHour.getDoctor();  // Get the doctor ID from the DTO
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            // Loop through all the dates from today to the workDate
            while (!today.isAfter(workDate)) {
                // Create a new Doctorworking object for each date
                Doctorworking newWorkingHour = new Doctorworking();
                newWorkingHour.setWorkDate(today);  // Set the work date to the current date
                newWorkingHour.setStartTime(workingHour.getStartTime());  // Set the start time
                newWorkingHour.setEndTime(workingHour.getEndTime());  // Set the end time
                newWorkingHour.setDoctor(doctor);  // Set the Doctor object (not just the ID)
                // Add the new working hour to the list
                workingHoursList.add(newWorkingHour);
                // Move to the next day
                today = today.plusDays(1);
            }
            // Save all the working hours at once
            return doctorWorkingHourRepository.saveAll(workingHoursList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Doctorworking updateWorkingHour(DoctorworkingDTO workingHourDTO) {
        try {
            // Tìm bản ghi cần cập nhật dựa trên ID
            Doctorworking existingWorkingHour = doctorWorkingHourRepository.findById(workingHourDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Working hour not found"));

            // Lấy thông tin bác sĩ nếu có cập nhật
            if (workingHourDTO.getDoctor() != null) {
                Doctor doctor = doctorRepository.findById(workingHourDTO.getDoctor())
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));
                existingWorkingHour.setDoctor(doctor);
            }

            // Cập nhật thông tin giờ làm việc
            existingWorkingHour.setWorkDate(workingHourDTO.getWorkDate());
            existingWorkingHour.setStartTime(workingHourDTO.getStartTime());
            existingWorkingHour.setEndTime(workingHourDTO.getEndTime());

            // Lưu lại dữ liệu đã cập nhật
            return doctorWorkingHourRepository.save(existingWorkingHour);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctorworking> getWorkingHoursByDoctor(int doctorId) {
        return doctorWorkingHourRepository.findByDoctorId(doctorId);
    }
    @Override
    // Xóa tất cả các bản ghi có doctor_id = id
    public void deleteWorkingHoursByDoctor(int doctorId) {
        doctorWorkingHourRepository.deleteByDoctorId(doctorId);
    }
}
