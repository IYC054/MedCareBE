package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Doctorworking;

import java.util.List;

public interface DoctorWorkingHourService {
    List<Doctorworking> getAllWorkingHours();
    Doctorworking getWorkingHour(int id);
    Doctorworking addWorkingHour(Doctorworking workingHour);
    Doctorworking updateWorkingHour(Doctorworking workingHour);
    void deleteWorkingHour(int id);
}
