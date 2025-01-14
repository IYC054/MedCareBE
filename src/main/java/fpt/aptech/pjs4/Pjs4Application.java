package fpt.aptech.pjs4;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("fpt.aptech.pjs4.repositories")
@EntityScan("fpt.aptech.pjs4.entities")

public class Pjs4Application {

    public static void main(String[] args) {
        SpringApplication.run(Pjs4Application.class, args);
    }
    @Bean
    Tesseract getTesseract(){
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\PC\\Desktop\\reatjs\\projectapi BE\\MedCareBE\\src\\main\\java\\tessdata");
        tesseract.setLanguage("eng"); // Chọn ngôn ngữ (English + Vietnamese)
        tesseract.setOcrEngineMode(1); // Sử dụng chế độ LSTM (LSTM OCR Engine Mode)
        tesseract.setPageSegMode(3); // Chế độ phân đoạn văn bản theo đoạn
        return tesseract;
    }
}
