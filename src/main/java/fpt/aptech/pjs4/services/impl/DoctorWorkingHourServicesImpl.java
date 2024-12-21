package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Doctorworking;
import fpt.aptech.pjs4.repositories.DoctorWorkingHourRepository;
import fpt.aptech.pjs4.services.DoctorWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Doctorworking> workingHour = doctorWorkingHourRepository.findById(id);
        return workingHour.orElse(null); // Trả về null nếu không tìm thấy
    }

    @Override
    public Doctorworking addWorkingHour(Doctorworking workingHour) {
        return doctorWorkingHourRepository.save(workingHour); // Lưu mới
    }

    @Override
    public Doctorworking updateWorkingHour(Doctorworking workingHour) {
        if (doctorWorkingHourRepository.existsById(workingHour.getId())) {
            return doctorWorkingHourRepository.save(workingHour); // Cập nhật nếu tồn tại
        } else {
            return null; // Trả về null nếu không tìm thấy
        }
    }

    @Override
    public void deleteWorkingHour(int id) {
        if (doctorWorkingHourRepository.existsById(id)) {
            doctorWorkingHourRepository.deleteById(id); // Xóa nếu tồn tại
        }
    }

    @Override
    public List<Doctorworking> getWorkingHoursByDoctor(int id) {
        return doctorWorkingHourRepository.findByDoctor_Id(id);
    }
}
