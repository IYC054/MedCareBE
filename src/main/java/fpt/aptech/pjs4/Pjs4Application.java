package fpt.aptech.pjs4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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

}
