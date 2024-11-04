package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.News;

import java.util.List;

public interface NewsService {
    News createNews(News news);
    News getNewsById(int id);
    List<News> getAllNews();
    News updateNews(int id, News news);
    void deleteNews(int id);
}
