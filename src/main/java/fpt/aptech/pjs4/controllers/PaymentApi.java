package fpt.aptech.pjs4.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.aptech.pjs4.DTOs.APIResponse;
import fpt.aptech.pjs4.DTOs.PaymentDTO;
import fpt.aptech.pjs4.DTOs.PaymentRequest;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.entities.Payment;
import fpt.aptech.pjs4.services.AppointmentService;
import fpt.aptech.pjs4.services.PaymentService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/payments")
public class PaymentApi {
    @Autowired
    private AppointmentService appointmentService;

    private final String url_mbbank = "https://online.mbbank.com.vn/api/retail-transactionms/transactionms/get-account-transaction-history";


    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/momo")
    public ResponseEntity<?> payWithMomo(@RequestBody PaymentRequest paymentRequest) {
        String accessKey = "LAUsmdNYCswc4xt3";
        String secretKey = "v4mYTJVM8M7pSUemgFTTqon3PopWekkD";
        String partnerCode = "MOMOEXFT20240911";
        String orderInfo = paymentRequest.getOrderInfo();
        String redirectUrl = "http://localhost:5173/payment-success?doctor="+paymentRequest.getDoctorId() + "&work="+ paymentRequest.getWorkId() +"&specialty="+ paymentRequest.getSpecialtyId() +"&profile=" +paymentRequest.getProfileId();
        String ipnUrl = "http://localhost:5173/payment-success?doctor="+paymentRequest.getDoctorId() + "&work="+ paymentRequest.getWorkId() +"&specialty="+ paymentRequest.getSpecialtyId() +"&profile=" +paymentRequest.getProfileId();
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

    @GetMapping("/transaction-history")
    public ResponseEntity<APIResponse<?>> getTransactionHistory(@RequestParam String accountphone, @RequestParam(required = false) Integer appointid) {
        APIResponse<Object> apiResponse = new APIResponse<>();
        RestTemplate restTemplate = new RestTemplate();
        String accountno = "0933315633";
        String sessionId = "2a146a83-ae93-43e0-89cb-c45515815ad3";
        String refno = accountno + "-202412237590493-88678";
        Date datenow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datenow);
        calendar.add(Calendar.DATE, -4);
        Date fromDate = calendar.getTime();

        // Payload cơ bản
        Map<String, Object> payload = new HashMap<>();
        payload.put("accountNo", accountno);
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
                    String refNoinTransaction = (String) transaction.get("refNo");
                    Payment checkrefno = paymentService.findPaymentByTransactionCode(refNoinTransaction);
                    if (checkrefno == null) {
                        System.out.println("giao dich chua ton tai trong db " + refNoinTransaction);
                        String creditAmountStr = (String) transaction.get("creditAmount");
                        amount = creditAmountStr != null ? new BigDecimal(creditAmountStr) : BigDecimal.ZERO;
                        String paymethod = "Ngân hàng";
                        String transactioncode = (String) transaction.get("refNo");
                        String transactiondescription = (String) transaction.get("description");
                        apiResponse.setMessage("giao dich chua xu ly " + transactioncode);
                        apiResponse.setResult(false);
                        if(appointid != null){
                            createPayment(appointid,amount, paymethod, transactioncode, transactiondescription);
                        }
                    }else{
                        apiResponse.setMessage("Chưa có giao dịch mới");
                        apiResponse.setResult(true);

                    }
                }
            }

            if (!matchingTransactions.isEmpty()) {
                // Nếu có giao dịch chứa số điện thoại trong description
                apiResponse.setCode(200);
//                apiResponse.setMessage("Các giao dịch chứa số điện thoại được tìm thấy.");
//                apiResponse.setResult(matchingTransactions);
            } else {
                // Nếu không có giao dịch chứa số điện thoại trong description
                apiResponse.setCode(404);
//                apiResponse.setMessage("Không tìm thấy giao dịch chứa số điện thoại.");
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
//    @GetMapping("/transaction-history")
//    public ResponseEntity<APIResponse<?>> getTransactionHistory() {
//            APIResponse<Object> apiResponse = new APIResponse<>();
//            RestTemplate restTemplate = new RestTemplate();
//            String accountno = "0933315633";
//            String sessionId = "";
//            String refno = accountno + "-202412237590493-88678";
//            Date datenow = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(datenow);
//            calendar.add(Calendar.DATE, -1);
//            Date fromDate = calendar.getTime();
//            // Payload cơ bản
//            Map<String, Object> payload = new HashMap<>();
//            payload.put("accountNo", "0933315633");
//            payload.put("fromDate", dateFormat.format(fromDate));
//            payload.put("toDate", dateFormat.format(datenow));
//            payload.put("sessionId", sessionId);
//            payload.put("refNo", refno);
//            payload.put("deviceIdCommon", "rgfgfmrr-mbib-0000-0000-2024122008495838");
//
//            // Header
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("User-Agent", "Mozilla/5.0");
//            headers.set("refno", refno);
//            headers.set("deviceid", "rgfgfmrr-mbib-0000-0000-2024122008495838");
//            headers.set("authorization", "Basic RU1CUkVUQUlMV0VCOlNEMjM0ZGZnMzQlI0BGR0AzNHNmc2RmNDU4NDNm");
//
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
//
//            try {
//                ResponseEntity<String> response = restTemplate.postForEntity(url_mbbank, request, String.class);
//                ObjectMapper objectMapper = new ObjectMapper();
//                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
//                // Xử lý kết quả thành công
//                apiResponse.setCode(200);
//                apiResponse.setMessage("Lấy data thành công.");
//                apiResponse.setResult(responseBody);
//                return ResponseEntity.ok(apiResponse);
//            } catch (Exception e) {
//                // Xử lý lỗi
//                apiResponse.setCode(500);
//                apiResponse.setMessage("Có lỗi xảy ra: " + e.getMessage());
//                apiResponse.setResult(null);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
//            }
//        }
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

}
