package fpt.aptech.pjs4.configs;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    public String generateQRCode(String appointmentDetails) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(appointmentDetails, BarcodeFormat.QR_CODE, 200, 200, hintMap);

        // Chuyển BitMatrix thành chuỗi mã QR hình ảnh (ví dụ: base64)
        StringBuilder qrCodeImage = new StringBuilder();
        for (int i = 0; i < bitMatrix.getHeight(); i++) {
            for (int j = 0; j < bitMatrix.getWidth(); j++) {
                qrCodeImage.append(bitMatrix.get(j, i) ? "1" : "0");
            }
        }

        return qrCodeImage.toString();  // Trả về chuỗi mã QR hoặc base64 của hình ảnh QR
    }
}
