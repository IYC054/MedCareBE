package fpt.aptech.pjs4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Pjs4Application {

    public static void main(String[] args) {
        SpringApplication.run(Pjs4Application.class, args);
    }

}
