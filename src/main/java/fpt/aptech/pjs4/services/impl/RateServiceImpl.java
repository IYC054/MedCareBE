package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.News;
import fpt.aptech.pjs4.entities.Rating;
import fpt.aptech.pjs4.repositories.NewsRepository;

import fpt.aptech.pjs4.repositories.RateReponsitory;
import fpt.aptech.pjs4.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateReponsitory rateRepository;

    @Override
    public Rating rateDoctor(Rating rate) {
        return rateRepository.save(rate);
    }

    @Override
    public Rating getRateById(int id) {
        return rateRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rating> getAllRate() {
        return rateRepository.findAll();
    }

    @Override
    public Rating updateRate(int id, Rating rate) {
        return rateRepository.findById(id).map(existingRate -> {
            existingRate.setRate(rate.getRate());
            existingRate.setDescription(rate.getDescription());
            existingRate.setPatient_id(rate.getPatient_id());
            existingRate.setDoctor_id(rate.getDoctor_id());
            return rateRepository.save(existingRate);
        }).orElse(null);
    }


    @Override
    public void deleteRate(int id) {
        rateRepository.deleteById(id);
    }


}
