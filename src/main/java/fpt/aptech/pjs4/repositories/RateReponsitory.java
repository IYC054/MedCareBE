package fpt.aptech.pjs4.repositories;


import fpt.aptech.pjs4.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateReponsitory extends JpaRepository<Rating, Integer> {

}
