package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.News;
import fpt.aptech.pjs4.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping
    public ResponseEntity<News> createNews(

            @RequestPart("date") String date,
            @RequestPart("description") String description,
            @RequestPart("images") MultipartFile image,
            @RequestPart("title") String title)
            throws IOException {
        // Tạo thư mục chứa hình ảnh nếu chưa tồn tại
        Path uploadDir = Paths.get("src/main/resources/static/image");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Lưu file hình ảnh
        String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path destinationPath = uploadDir.resolve(uniqueFileName);
        Files.write(destinationPath, image.getBytes());

        // Tạo URL cho hình ảnh
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(uniqueFileName)
                .toUriString();

        // Tạo đối tượng News mới
        News news = new News();
        news.setTitle(title);
        news.setDescription(description);

        news.setImages(imageUrl); // Gán URL cho hình ảnh
        news.setDate(LocalDate.parse(date));// Nếu `date` là chuỗi theo định dạng "yyyy-MM-dd"
        // Lưu News
        News createdNews = newsService.createNews(news);

        // Trả về response với mã status 201 và dữ liệu của News vừa tạo
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
