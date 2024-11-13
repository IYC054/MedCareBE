package fpt.aptech.pjs4.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration

public class SecurityConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//
//                .authorizeRequests()
//                .anyRequest().permitAll();

//                .anyRequest().authenticated()
//                .and()
//                .formLogin(AbstractHttpConfigurer::disable);


       // return http.build();
        http
        .csrf(csrf -> csrf.disable()) // Tắt CSRF cho REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**", // Đường dẫn Swagger UI mới
                                "/v3/api-docs/**", // API docs của Swagger OpenAPI 3
                                "/swagger-resources/**", // Các tài nguyên Swagger
                                "/webjars/**", // Các thư viện của Swagger
                                "/api/**"
                        ).permitAll() // Cho phép truy cập mà không cần xác thực
                        .anyRequest().authenticated() // Bắt buộc xác thực cho các endpoint khác
                )
                .formLogin(AbstractHttpConfigurer::disable); // Định nghĩa cơ chế xác thực cho các API khác
        return http.build();
    }
}