package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.DoctorWorking;
import fpt.aptech.pjs4.repositories.DoctorWorkingHourRepository;
import fpt.aptech.pjs4.services.DoctorWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorWorkingHourServicesImpl implements DoctorWorkingHourService {
    @Autowired
    private DoctorWorkingHourRepository doctorWorkingHourRepository;
    @Override
    public List<DoctorWorking> getAllWorkingHours() {
        return doctorWorkingHourRepository.findAll();
    }

    @Override
    public DoctorWorking getWorkingHour(int id) {
        return null;
    }

    @Override
    public DoctorWorking addWorkingHour(DoctorWorking workingHour) {
        return null;
    }

    @Override
    public DoctorWorking updateWorkingHour(DoctorWorking workingHour) {
        return null;
    }

    @Override
    public void deleteWorkingHour(int id) {

    }
}
