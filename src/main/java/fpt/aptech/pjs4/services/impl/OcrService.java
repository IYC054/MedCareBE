package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.DTOs.OcrResult;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Base64;

@Service
public class OcrService {

    @Autowired
    private Tesseract tesseract;

    // Cập nhật phương thức OCR để sử dụng imageString
    public OcrResult ocr(String imageString) throws IOException, TesseractException {
        // Giải mã Base64 thành BufferedImage
        BufferedImage image = decodeBase64ToImage(imageString);

        // Tiền xử lý ảnh
        BufferedImage processedImage = preprocessImage(image);

        // Nhận diện chữ trên ảnh đã tiền xử lý
        String text = tesseract.doOCR(processedImage);

        // Loại bỏ tất cả khoảng trắng (space) trong kết quả nhận diện
        text = text.replaceAll("\\s+", "");

        // Lưu kết quả vào OcrResult
        OcrResult ocrResult = new OcrResult();
        ocrResult.setResult(text);

        return ocrResult;
    }

    // Hàm giải mã Base64 thành BufferedImage
    private BufferedImage decodeBase64ToImage(String imageString) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bis);
    }

    // Phương thức tiền xử lý ảnh mạnh mẽ hơn
    private BufferedImage preprocessImage(BufferedImage image) throws IOException {
        // Chuyển ảnh thành grayscale
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Tăng độ tương phản ảnh
        RescaleOp rescaleOp = new RescaleOp(1.5f, 0, null); // Tăng độ sáng và độ tương phản
        grayImage = rescaleOp.filter(grayImage, null);

        // Thresholding: Chuyển ảnh thành đen trắng rõ ràng hơn
        BufferedImage binarizedImage = new BufferedImage(grayImage.getWidth(), grayImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < grayImage.getWidth(); x++) {
            for (int y = 0; y < grayImage.getHeight(); y++) {
                int pixel = grayImage.getRGB(x, y);
                Color color = new Color(pixel);
                int brightness = color.getRed();
                if (brightness < 128) {
                    binarizedImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    binarizedImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        // Loại bỏ nhiễu bằng cách làm mờ ảnh (Gaussian Blur)
        BufferedImage blurredImage = applyGaussianBlur(binarizedImage);

        // Lưu ảnh đã xử lý vào file tạm thời
        File tempFile = new File("temp_processed_image.png");
        ImageIO.write(blurredImage, "PNG", tempFile);

        System.out.println("Processed image saved to: " + tempFile.getAbsolutePath());

        return blurredImage;
    }

    // Hàm áp dụng Gaussian Blur
    private BufferedImage applyGaussianBlur(BufferedImage image) {
        float[] matrix = {
                1f/16f, 2f/16f, 1f/16f,
                2f/16f, 4f/16f, 2f/16f,
                1f/16f, 2f/16f, 1f/16f
        };
        BufferedImageOp blurOp = new ConvolveOp(new Kernel(3, 3, matrix));
        return blurOp.filter(image, null);
    }
}
