package fpt.aptech.pjs4.services;


import fpt.aptech.pjs4.entities.Rating;

import java.util.List;

public interface RateService {
    Rating rateDoctor(Rating rate);

    Rating getRateById(int id);

    List<Rating> getAllRate();

    Rating updateRate(int id, Rating rate);

    void deleteRate(int id);


}
