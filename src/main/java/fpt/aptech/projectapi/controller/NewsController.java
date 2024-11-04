package fpt.aptech.projectapi.controller;

import fpt.aptech.projectapi.entities.News;
import fpt.aptech.projectapi.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/news")
@AllArgsConstructor
public class NewsController {
    private NewsService newsService;

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        News createdNews = newsService.createNews(news);
        return ResponseEntity.status(201).body(createdNews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable int id) {
        News news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        return ResponseEntity.ok(newsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable int id, @RequestBody News news) {
        News updatedNews = newsService.updateNews(id, news);
        return ResponseEntity.ok(updatedNews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable int id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
