package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.DoctorWorking;

import java.util.List;

public interface DoctorWorkingHourService {
    List<DoctorWorking> getAllWorkingHours();
    DoctorWorking getWorkingHour(int id);
    DoctorWorking addWorkingHour(DoctorWorking workingHour);
    DoctorWorking updateWorkingHour(DoctorWorking workingHour);
    void deleteWorkingHour(int id);
}
