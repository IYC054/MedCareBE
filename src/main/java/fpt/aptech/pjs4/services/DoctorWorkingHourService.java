package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.DoctorworkingDTO;
import fpt.aptech.pjs4.entities.Doctorworking;

import java.util.List;

public interface DoctorWorkingHourService {
    List<Doctorworking> getAllWorkingHours();
    Doctorworking getWorkingHour(int id);
    List<Doctorworking> addWorkingHour(DoctorworkingDTO workingHour);
    Doctorworking updateWorkingHour(Doctorworking workingHour);
    void deleteWorkingHour(int id);
    List<Doctorworking> getWorkingHoursByDoctor(int id);
}
