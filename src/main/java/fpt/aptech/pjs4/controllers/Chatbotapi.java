//package fpt.aptech.pjs4.controllers;
//
//import fpt.aptech.pjs4.DTOs.QuestionRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("api/chatbot")
//public class Chatbotapi {
//    private final RestTemplate restTemplate = new RestTemplate();
//    private static final String API_URL = "https://api.openai.com/v1/completions"; // Thay thế bằng URL API của tôi
//
//    @PostMapping("/ask")
//    public ResponseEntity<String> askQuestion(@RequestBody QuestionRequest request) {
//        // Thiết lập Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth("ABCDE");
//
//        // Tạo body cho request
//        Map<String, Object> body = new HashMap<>();
//        body.put("model", "gpt-3.5-turbo"); // Model hợp lệ
//        body.put("prompt", request.getQuestion());
//        body.put("max_tokens", 100); // Giới hạn số lượng tokens
//        body.put("temperature", 0.7); // Độ sáng tạo
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
//
//        try {
//            // Gửi request tới OpenAI API
//            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
//
//            // Trả về kết quả từ API
//            return ResponseEntity.ok(response.getBody());
//        } catch (Exception e) {
//            // Xử lý lỗi
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }
//}
