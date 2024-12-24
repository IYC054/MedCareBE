package fpt.aptech.pjs4.configs;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.enums.Role;
import fpt.aptech.pjs4.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;


@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository) {
        var role = new HashSet<String>();
        role.add(Role.ADMIN.name());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return args -> {
            if(accountRepository.findAccountByEmail("admin@gmail.com").isEmpty()){
                Account account = new Account();
                account.setEmail("admin@gmail.com");
                account.setPassword(passwordEncoder.encode("admin"));
                account.setRole(role);
                accountRepository.save(account);
                System.out.println("Da tạo ADMIN_ tạo mặc định ");
            }
        };
    }
}
