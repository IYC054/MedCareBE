package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Doctorworking;
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
    public List<Doctorworking> getAllWorkingHours() {
        return doctorWorkingHourRepository.findAll();
    }

    @Override
    public Doctorworking getWorkingHour(int id) {
        return null;
    }

    @Override
    public Doctorworking addWorkingHour(Doctorworking workingHour) {
        return null;
    }

    @Override
    public Doctorworking updateWorkingHour(Doctorworking workingHour) {
        return null;
    }

    @Override
    public void deleteWorkingHour(int id) {

    }
}
