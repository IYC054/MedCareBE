package fpt.aptech.pjs4.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import fpt.aptech.pjs4.DTOs.Notification;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
    public ResponseEntity<News> updateNews(
            @PathVariable int id,
            @RequestPart(value = "date", required = false) String date,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart(value = "title", required = false) String title,
            @RequestPart(value = "images", required = false) MultipartFile image) throws IOException {

        // Lấy thông tin bài viết hiện tại
        News existingNews = newsService.getNewsById(id);
        if (existingNews == null) {
            return ResponseEntity.notFound().build();
        }

        // Cập nhật thông tin nếu có dữ liệu mới
        if (title != null) {
            existingNews.setTitle(title);
        }
        if (description != null) {
            existingNews.setDescription(description);
        }
        if (date != null) {
            existingNews.setDate(LocalDate.parse(date));
        }

        // Xử lý cập nhật hình ảnh nếu có ảnh mới
        if (image != null && !image.isEmpty()) {
            Path uploadDir = Paths.get("src/main/resources/static/image");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Lưu ảnh mới
            String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path destinationPath = uploadDir.resolve(uniqueFileName);
            Files.write(destinationPath, image.getBytes());

            // Tạo URL ảnh mới
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image/")
                    .path(uniqueFileName)
                    .toUriString();

            existingNews.setImages(imageUrl);
        }

        // Cập nhật bài viết
        News updatedNews = newsService.updateNews(id, existingNews);
        return ResponseEntity.ok(updatedNews);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable int id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/noti/{userId}")
    public List<Map<String, Object>> getNotifications(@PathVariable String userId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference notificationsRef = db.collection("notifications");

        // Lọc theo userId
        Query query = notificationsRef.whereEqualTo("userId", userId).orderBy("timestamp", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Map<String, Object>> notifications = new ArrayList<>();
        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            Map<String, Object> data = doc.getData();
            if (data != null) {
                data.put("id", doc.getId()); // Thêm documentId vào dữ liệu trả về
                notifications.add(data);
            }
        }

        return notifications;
    }

}
