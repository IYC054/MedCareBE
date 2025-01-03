package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.BHYTRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/bhyt")
public class BHYTController {

    @PostMapping("/check")
    public ResponseEntity<?> checkBHYT( @RequestParam("txtMaThe") String txtMaThe,
                                        @RequestParam("txtHoTen") String txtHoTen,
                                        @RequestParam("tokenRecaptch") String tokenRecaptch,
                                        @RequestParam("txtNgaySinh") String txtNgaySinh) {
        String url = "https://baohiemxahoi.gov.vn/UserControls/BHXH/BaoHiemYTe/TheBHYT/pListTheBHYTThe.aspx";

        // Tạo RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Tạo headers nếu cần
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String body = String.format(
                "txtMaThe=%s&txtHoTen=%s&txtNgaySinh=%s&tokenRecaptch=%s",
                txtMaThe,
                txtHoTen,
                txtNgaySinh,
                tokenRecaptch
        );
        // Tạo HttpEntity với headers và body
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            // Gửi yêu cầu POST
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // Trả về phản hồi từ server
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            // Xử lý lỗi
            return ResponseEntity.status(500).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }
}
