package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PaymentDataDTO;
import fpt.aptech.pjs4.DTOs.PaymentRequest;
import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/payment")
public class PaymentApi {
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/momo")
    public ResponseEntity<?> payWithMomo(@RequestBody PaymentRequest paymentRequest) {
        String accessKey = "mukihs6i0gwJ7hsq";
        String secretKey = "yKncdYiiEfUPOUjLLe339ePqrYbHV4Kf";
        String partnerCode = "MOMOELHN20241211";
        String orderInfo = paymentRequest.getOrderInfo();
        String redirectUrl = "http://localhost:5173/payment-success";
        String ipnUrl = "http://localhost:5173/payment-success";
        String requestType = "captureWallet";
        BigDecimal amount = paymentRequest.getAmount();
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = orderId;
        String extraData = "";
        String orderGroupId = "";
        boolean autoCapture = true;
        String lang = "vi";
        Payment paymenttrans = paymentService.getPaymentbyDescription(paymentRequest.getOrderInfo());
        if (paymenttrans != null) {
            return ResponseEntity.status(400).body("Payment already exists");
        }
        try {
            // Build rawSignature
            String rawSignature = "accessKey=" + accessKey +
                    "&amount=" + amount +
                    "&extraData=" + extraData +
                    "&ipnUrl=" + ipnUrl +
                    "&orderId=" + orderId +
                    "&orderInfo=" + orderInfo +
                    "&partnerCode=" + partnerCode +
                    "&redirectUrl=" + redirectUrl +
                    "&requestId=" + requestId +
                    "&requestType=" + requestType;

            System.out.println("rawSignature" + rawSignature);
            // Create HMAC SHA256 signature
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(rawSignature.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexHash = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexHash.append('0');
                hexHash.append(hex);
            }
            String signature = hexHash.toString();
            System.out.println("Signature: " + signature);
            // Create request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("partnerCode", partnerCode);
            requestBody.put("partnerName", "Test");
            requestBody.put("storeId", "MomoTestStore");
            requestBody.put("requestId", requestId);
            requestBody.put("amount", amount);
            requestBody.put("orderId", orderId);
            requestBody.put("orderInfo", orderInfo);
            requestBody.put("redirectUrl", redirectUrl);
            requestBody.put("ipnUrl", ipnUrl);
            requestBody.put("lang", lang);
            requestBody.put("requestType", requestType);
            requestBody.put("autoCapture", autoCapture);
            requestBody.put("extraData", extraData);
            requestBody.put("orderGroupId", orderGroupId);
            requestBody.put("signature", signature);
            requestBody.put("appointmentid", 1);

            // Call MoMo API
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            String momoApiUrl = "https://payment.momo.vn/v2/gateway/api/create";
            ResponseEntity<Map> response = restTemplate.postForEntity(momoApiUrl, entity, Map.class);
            System.out.println(response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    public PaymentDataDTO convertToDTO(Payment payment) {
        PaymentDataDTO dto = new PaymentDataDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setTransactionDate(payment.getTransactionDate() != null ?
                payment.getTransactionDate().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null);
        dto.setTransactionDescription(payment.getTransactionDescription());
        dto.setAppointmentId(payment.getAppointment().getId());
        dto.setPatientName(payment.getAppointment().getPatient().getAccount().getName());
        dto.setDoctorName(payment.getAppointment().getDoctor().getAccount().getName());
        return dto;
    }

}
