package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
