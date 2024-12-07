package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.PaymentRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class MomoApi {

    @PostMapping("/momo")
    public ResponseEntity<?> payWithMomo(@RequestBody PaymentRequest paymentRequest) {
        String accessKey = "F8BBA842ECF85";
        String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
        String partnerCode = "MOMO";
        String orderInfo = paymentRequest.getOrderInfo();
        String redirectUrl = "https://webhook.site/b3088a6a-2d17-4f8d-a383-71389a6c600b";
        String ipnUrl = "https://webhook.site/b3088a6a-2d17-4f8d-a383-71389a6c600b";
        String requestType = "payWithMethod";
        BigDecimal amount = paymentRequest.getAmount();
        String orderId = partnerCode + System.currentTimeMillis();
        String requestId = orderId;
        String extraData = "";
        String orderGroupId = "";
        boolean autoCapture = true;
        String lang = "vi";

        try {
            // Build rawSignature
            var rawSignature =
                    "accessKey=" +
                            accessKey +
                            "&amount=" +
                            amount +
                            "&extraData=" +
                            extraData +
                            "&ipnUrl=" +
                            ipnUrl +
                            "&orderId=" +
                            orderId +
                            "&orderInfo=" +
                            orderInfo +
                            "&partnerCode=" +
                            partnerCode +
                            "&redirectUrl=" +
                            redirectUrl +
                            "&requestId=" +
                            requestId +
                            "&requestType=" +
                            requestType;

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

            // Call MoMo API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            String momoApiUrl = "https://test-payment.momo.vn/v2/gateway/api/create";
            ResponseEntity<Map> response = restTemplate.postForEntity(momoApiUrl, entity, Map.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}