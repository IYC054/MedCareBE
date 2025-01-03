package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.News;
import fpt.aptech.pjs4.repositories.NewsRepository;
import fpt.aptech.pjs4.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News getNewsById(int id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News updateNews(int id, News news) {
        if (newsRepository.existsById(id)) {
            news.setId(id);
            return newsRepository.save(news);
        }
        return null;
    }

    @Override
    public void deleteNews(int id) {
        newsRepository.deleteById(id);
    }
}
