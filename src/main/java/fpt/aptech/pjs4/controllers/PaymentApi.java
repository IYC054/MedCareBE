package fpt.aptech.pjs4.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.aptech.pjs4.DTOs.*;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.services.AppointmentService;
import fpt.aptech.pjs4.services.PaymentService;
import fpt.aptech.pjs4.services.impl.OcrService;
import fpt.aptech.pjs4.services.impl.VNPAYService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import net.sourceforge.tess4j.TesseractException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
@RestController
@RequestMapping("/api/payments")
public class PaymentApi {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private OcrService ocrService;
    private final String url_mbbank = "https://online.mbbank.com.vn/api/retail-transactionms/transactionms/get-account-transaction-history";


    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/momo")
    public ResponseEntity<?> payWithMomo(@RequestBody PaymentRequest2 paymentRequest) {
//        String accessKey = "LAUsmdNYCswc4xt3";
//        String secretKey = "v4mYTJVM8M7pSUemgFTTqon3PopWekkD";
//        String partnerCode = "MOMOEXFT20240911";
        String accessKey = "LAUsmdNYCswc4xt3";
        String secretKey = "v4mYTJVM8M7pSUemgFTTqon3PopWekkD";
        String partnerCode = "MOMOEXFT20240911";
        String orderInfo = paymentRequest.getOrderInfo();
        String redirectUrl = "http://localhost:5173/payment-success?doctor="+paymentRequest.getDoctorId() + "&work="+ paymentRequest.getWorkId() +"&specialty="+ paymentRequest.getSpecialtyId() +"&profile=" +paymentRequest.getProfileId() + "&email=" + paymentRequest.getDoctorEmail();
        String ipnUrl = "http://localhost:5173/payment-success?doctor="+paymentRequest.getDoctorId() + "&work="+ paymentRequest.getWorkId() +"&specialty="+ paymentRequest.getSpecialtyId() +"&profile=" +paymentRequest.getProfileId()  + "&email=" + paymentRequest.getDoctorEmail();
        String requestType = "captureWallet";
        BigDecimal amount = paymentRequest.getAmount();
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = orderId;
        String extraData = "";
        String orderGroupId = "";
        boolean autoCapture = true;
        String lang = "vi";

//        Payment paymenttrans = paymentService.getPaymentbyDescription(paymentRequest.getOrderInfo());
//        if (paymenttrans != null) {
//            return ResponseEntity.status(400).body("Payment already exists");
//        }

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

            System.out.println("Request body: " + requestBody); // Log requestBody

            // Send the request to MoMo API
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            String momoApiUrl = "https://payment.momo.vn/v2/gateway/api/create";
            ResponseEntity<Map> response = restTemplate.postForEntity(momoApiUrl, entity, Map.class);
            System.out.println("Response from MoMo API: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/momo-mobile")
    public ResponseEntity<?> payWithMomoMobile(
            @RequestParam BigDecimal amount,
            @RequestParam String orderInfo) {

        String accessKey = "LAUsmdNYCswc4xt3";
        String secretKey = "v4mYTJVM8M7pSUemgFTTqon3PopWekkD";
        String partnerCode = "MOMOEXFT20240911";
        String redirectUrl = "medcaremobile://payment-success";
        String ipnUrl = "medcaremobile://payment-success";
        String requestType = "captureWallet";
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = orderId;
        String extraData = "";
        String orderGroupId = "";
        boolean autoCapture = true;
        String lang = "vi";


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

            System.out.println("Request body: " + requestBody); // Log requestBody

            // Send the request to MoMo API
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            String momoApiUrl = "https://payment.momo.vn/v2/gateway/api/create";
            ResponseEntity<Map> response = restTemplate.postForEntity(momoApiUrl, entity, Map.class);
            System.out.println("Response from MoMo API: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
//    @PostMapping("/upload")
//    public ResponseEntity<APIResponse<?>> upload() throws IOException, TesseractException {
//        APIResponse apiResponse = new APIResponse();
//        String url_getimage = "https://online.mbbank.com.vn/api/retail-web-internetbankingms/getCaptchaImage";
//        String url_login = "https://online.mbbank.com.vn/api/retail_web/internetbanking/v2.0/doLogin";
//        String deviceIdCommon = "rgfgfmrr-mbib-0000-0000-2024122008495838";
//        String refno = "0933315633-202412237590493-88678";
//
//        // Tạo payload cho bước 1
//        Map<String, Object> payload = new HashMap<>();
//        payload.put("refNo", refno);
//        payload.put("deviceIdCommon", deviceIdCommon);
//        payload.put("sessionId", "");
//
//        // Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("User-Agent", "Mozilla/5.0");
//        headers.set("refno", refno);
//        headers.set("deviceid", deviceIdCommon);
//        headers.set("authorization", "Basic RU1CUkVUQUlMV0VCOlNEMjM0ZGZnMzQlI0BGR0AzNHNmc2RmNDU4NDNm");
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
//
//        try {
//            // Gửi yêu cầu để lấy captcha
//            ResponseEntity<String> response = restTemplate.postForEntity(url_getimage, request, String.class);
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
//            String description = (String) responseBody.get("imageString");
//
//            // OCR xử lý captcha
//            OcrResult ocrResult = ocrService.ocr(description);
//            String resultCaptcha = ocrResult.getResult();
//            System.out.println("Captcha giải đc " + resultCaptcha);
//            // Tạo requestData để mã hóa
//            Map<String, Object> requestData = new HashMap<>();
//            requestData.put("userId", "0933315633");
//            requestData.put("password", hashPassword("Minhduy@01"));
//            requestData.put("captcha", resultCaptcha);
//            requestData.put("ibAuthen2faString", "c7a1beebb9400375bb187daa33de9659");
//            requestData.put("sessionId", null);
//            requestData.put("refNo", refno);
//            requestData.put("deviceIdCommon", deviceIdCommon);
//            requestData.put("dataEnc", "3/ywqPD2MVvb6jfSBhz7flNpkhPDOBEGqj0K4/F9Jk+LDlboMGZJm15XG7LoWHZ0HqUf7BN7S0wmjy+cf6eABnqb6cmXn4bLqiPEksDMtrurUHnY60ZhX4nluBuskp3CLUFqVWtW11G7eY8ocuy7yKAZJ0uNz4jKRtuFRP+BRs3HPwz5RO0YaxXcC2tLya72+geChqrh98qP3+lNRN/z0+PUORyjVTkm5zjZ8iFjK63qTkF0plXfnR8Yo2UkjxpcsVWE7aAoPYLHROlozB4N3i65xSrWdd7loIbjmO1FoWLi26DJP1lGSXqx9EUBEEdAAuJKBv4/HLKejV9ZK0lXZ/I7wMgpt+ueJcLQTdWsO8WI6ybdi+CYHa6w+wpKhudFUU41tSGPYlkj6ywa0h6E6WppO6jaoQi+wO6hiOsRPeoKM3ezfMGs5fw79J6JFcIR");
//
//
//            HttpHeaders loginHeaders = new HttpHeaders();
//            loginHeaders.setContentType(MediaType.APPLICATION_JSON);
//            loginHeaders.set("User-Agent", "MMozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
//            loginHeaders.set("refno", refno);
//            loginHeaders.set("deviceid", deviceIdCommon);
//            loginHeaders.set("authorization", "Basic RU1CUkVUQUlMV0VCOlNEMjM0ZGZnMzQlI0BGR0AzNHNmc2RmNDU4NDNm");
//            loginHeaders.set("Origin", "https://online.mbbank.com.vn");
//            loginHeaders.set("Referer", "https://online.mbbank.com.vn/");
//            loginHeaders.set("x-request-id", refno);
//            loginHeaders.set("App", "MB_WEB");
//
//            // Body đã mã hóa
//
//
//            HttpEntity<Map<String, Object>> loginRequest = new HttpEntity<>(requestData, loginHeaders);
//
//            // Gửi yêu cầu đăng nhập
//            ResponseEntity<String> loginResponse = restTemplate.postForEntity(url_login, loginRequest, String.class);
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String, Object> loginResponseBody = mapper.readValue(loginResponse.getBody(), Map.class);
//
//            // Giải mã chuỗi "result" (nội dung JSON escaped)
//            // Trả về dữ liệu đã giải mã
//            apiResponse.setResult(loginResponseBody);
//            apiResponse.setCode(200);
//            return ResponseEntity.ok(apiResponse);
//
//        } catch (Exception e) {
//            apiResponse.setCode(500);
//            apiResponse.setMessage("Có lỗi xảy ra: " + e.getMessage());
//            apiResponse.setResult(null);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
//        }
//    }


    @GetMapping("/transaction-history")
    public ResponseEntity<APIResponse<?>> getTransactionHistory(@RequestParam String accountphone, @RequestParam(required = false) Integer appointid) {
        APIResponse<Object> apiResponse = new APIResponse<>();
        RestTemplate restTemplate = new RestTemplate();
        String accountno = "0933315633";
        String sessionId = "14ba080e-6fde-4450-8813-e69671548d82";
        String devicecommon = "rgfgfmrr-mbib-0000-0000-2024122008495838";
        String refno = accountno + "-2025011910271539-89948";
        Date datenow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datenow);
        calendar.add(Calendar.DATE, -1);
        Date fromDate = calendar.getTime();

        // Payload cơ bản
        Map<String, Object> payload = new HashMap<>();
        payload.put("accountNo", accountno);
        payload.put("fromDate", dateFormat.format(fromDate));
        payload.put("toDate", dateFormat.format(datenow));
        payload.put("sessionId", sessionId);
        payload.put("refNo", refno);
        payload.put("deviceIdCommon", devicecommon);

        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "Mozilla/5.0");
        headers.set("refno", refno);
        headers.set("deviceid", devicecommon);
        headers.set("authorization", "Basic RU1CUkVUQUlMV0VCOlNEMjM0ZGZnMzQlI0BGR0AzNHNmc2RmNDU4NDNm");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url_mbbank, request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);

            // Lấy transactionHistoryList từ body
            List<Map<String, Object>> transactionHistoryList = (List<Map<String, Object>>) responseBody.get("transactionHistoryList");
            List<Map<String, Object>> matchingTransactions = new ArrayList<>();

            BigDecimal amount;
            if (transactionHistoryList != null) {
                for (Map<String, Object> transaction : transactionHistoryList) {
                    // Lấy description từ transaction
                    String description = (String) transaction.get("description");

                    // Kiểm tra nếu description không rỗng và chứa số điện thoại
                    if (description.contains(accountphone)) {
                        matchingTransactions.add(transaction);

                    }

                    for (Map<String, Object> matchingtransaction : matchingTransactions) {
                        String refNoinTransaction = (String) matchingtransaction.get("refNo");
                        Payment checkrefno = paymentService.findPaymentByTransactionCode(refNoinTransaction);

                        if (checkrefno == null && description.contains(accountphone)) {
                            String creditAmountStr = (String) transaction.get("creditAmount");
                            amount = creditAmountStr != null ? new BigDecimal(creditAmountStr) : BigDecimal.ZERO;
                            String paymethod = "Ngân hàng";
                            String transactioncode = (String) transaction.get("refNo");
                            String transactiondescription = (String) transaction.get("description");
                            apiResponse.setMessage("Có giao dịch mới");
                            apiResponse.setResult(true);
                            if(appointid != null){
                                createPayment(appointid,amount, paymethod, transactioncode, transactiondescription);
                            }
                        }else{
                            Payment checkrefno2 = paymentService.findPaymentByTransactionCode(refNoinTransaction);

                            apiResponse.setResult(checkrefno);

                        }
                    }


                }
            }
//            apiResponse.setResult(responseBody);
            if (!matchingTransactions.isEmpty()) {
                // Nếu có giao dịch chứa số điện thoại trong description
                apiResponse.setCode(200);
//                apiResponse.setMessage("Các giao dịch chứa số điện thoại được tìm thấy.");
//                apiResponse.setResult(matchingTransactions);
            } else {
                // Nếu không có giao dịch chứa số điện thoại trong description
                apiResponse.setCode(404);
                apiResponse.setMessage("Không tìm thấy giao dịch chứa số điện thoại.");
            }

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            // Xử lý lỗi
            apiResponse.setCode(500);
            apiResponse.setMessage("Có lỗi xảy ra: " + e.getMessage());
            apiResponse.setResult(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
    @GetMapping("/get-history")
    public ResponseEntity<?> getTransactionHistory() {
        System.out.println("Lay lich su giao dich");
            APIResponse<Object> apiResponse = new APIResponse<>();
            RestTemplate restTemplate = new RestTemplate();
            String accountno = "0933315633";
            String sessionId = "85c850f5-00da-46e6-8217-ab64d71e8305";
            String refno = accountno + "-202412237590493-88678";
            Date datenow = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(datenow);
            calendar.add(Calendar.DATE, -1);
            Date fromDate = calendar.getTime();
            // Payload cơ bản
            Map<String, Object> payload = new HashMap<>();
            payload.put("accountNo", "0933315633");
            payload.put("fromDate", dateFormat.format(fromDate));
            payload.put("toDate", dateFormat.format(datenow));
            payload.put("sessionId", sessionId);
            payload.put("refNo", refno);
            payload.put("deviceIdCommon", "rgfgfmrr-mbib-0000-0000-2024122008495838");

            // Header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("User-Agent", "Mozilla/5.0");
            headers.set("refno", refno);
            headers.set("deviceid", "rgfgfmrr-mbib-0000-0000-2024122008495838");
            headers.set("authorization", "Basic RU1CUkVUQUlMV0VCOlNEMjM0ZGZnMzQlI0BGR0AzNHNmc2RmNDU4NDNm");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url_mbbank, request, String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
                List<Map<String, Object>> transactionHistoryList = (List<Map<String, Object>>) responseBody.get("transactionHistoryList");
                List<Map<String, Object>> matchingTransactions = new ArrayList<>();
                // Xử lý kết quả thành công
                apiResponse.setCode(200);
                apiResponse.setMessage("Lấy data thành công.");
                apiResponse.setResult(responseBody);
                return ResponseEntity.ok(apiResponse);
            } catch (Exception e) {
                // Xử lý lỗi
                apiResponse.setCode(500);
                apiResponse.setMessage("Có lỗi xảy ra: " + e.getMessage());
                apiResponse.setResult(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("false");
            }
        }

    public boolean createPayment(Integer appointmentid,BigDecimal amount, String paymentMethod, String transactioncode, String transactiondescription) {
    try {
        Appointment appointment = appointmentService.getAppointmentById(appointmentid);
        if (appointment == null) {
            return false;
        }
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, zoneId);
        Instant transactionDate = localDateTime.atZone(zoneId).toInstant();
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setTransactionCode(transactioncode);
        payment.setStatus("Chờ xử lý");
        payment.setTransactionDescription(transactiondescription);
        payment.setTransactionDate(transactionDate);
        payment.setAppointment(appointment);

        paymentService.createPayment(payment);
        return true;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/create-payment")
    public ResponseEntity<?> submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = VNPAYService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return ResponseEntity.ok(vnpayUrl);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<Payment> updateAppointmentStatus(@PathVariable int id, @RequestBody Appointment appointment) {
        String status = appointment.getStatus();
        Payment updatedPayment = paymentService.updatePaymentStatusOnly(id, status);
        return ResponseEntity.ok(updatedPayment);
    }

}
