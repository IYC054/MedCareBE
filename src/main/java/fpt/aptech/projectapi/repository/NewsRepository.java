package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
