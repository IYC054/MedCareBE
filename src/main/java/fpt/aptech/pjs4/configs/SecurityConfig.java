package fpt.aptech.pjs4.configs;

import fpt.aptech.pjs4.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    protected static  final String SIGNER_KEY="5e3b6f9e67e9f1e3b6ad775d9a1c9078c9078b72ad34d3e4e745fb6b64367861";

    private  final String[] PUBLIC_ENPOINT_GET={
            "/api/image/**",
            "/api/news/**",
            "/api/doctors/**",
            "/api/payments/**",
            "/api/specialty/**",
            "/api/patients/**",
            "/api/hospital/**",
            "/api/workinghours/**",
            "/api/filesimage/**",
            "/appointment/details/**",
            "/vip-appointment/details/**",
            "/api/patientsfile/**",
            "/api/patientsprofile/**",
            "/api/appointment/**",
            "/api/doctors/**",
            "/api/vip-appointments/**",
            "/api/account/find",
            "/api/rates/**",
            "/api/messages/**",
            "api/chat/**"
    };

    private  final String[] PUBLIC_ENPOINT_POST={
            "/api/account/token",
            "/api/account/register",
            "/api/account",
            "/api/account/find",
            "/api/auth/send",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/api/auth/verify",
            "/api/feedbacks/**",
            "/api/patients/**",
            "/api/payments/**",
            "/api/payments/momo",
            "/api/payments/confirm-payment",
            "/api/appointment/**",
            "/api/vip-appointments/**",
            "/api/rates/**",
            "api/bhyt/check",
            "/api/cvimage/**",
            "/api/rates",
            "/api/account/**",
            "/api/vip-appointments/**",
            "/api/messages/**",
            "api/chat/**"

    };
    private  final String[] PUBLIC_ENPOINT_UPDATE={
            "/api/appointment/**",
            "/api/vip-appointments/**",
            "/api/payments/**",
            "/api/account/**",

    };
    private  final String[] PUBLIC_ENPOINT_DELETE={
            "/api/vip-appointments/**",
            "/api/payments/**",

    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
                    var cors = new org.springframework.web.cors.CorsConfiguration();
                    cors.setAllowedOrigins(List.of("http://localhost:5173"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    cors.setAllowedHeaders(List.of("*"));
                    cors.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    cors.setAllowCredentials(true);
                    return cors;
                }))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(HttpMethod.POST, PUBLIC_ENPOINT_POST).permitAll()
                                .requestMatchers(HttpMethod.GET, PUBLIC_ENPOINT_GET).permitAll()
                                .requestMatchers(HttpMethod.PUT, PUBLIC_ENPOINT_UPDATE).permitAll()
                                .requestMatchers(HttpMethod.DELETE, PUBLIC_ENPOINT_DELETE).permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .requestMatchers("/ws").permitAll()

                                //.requestMatchers(HttpMethod.GET, "/api/account").hasRole(Role.ADMIN.name())
                                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(
                                jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                .csrf(csrfConfigurer -> csrfConfigurer.disable());
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return converter;
    }
    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return  NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

}

