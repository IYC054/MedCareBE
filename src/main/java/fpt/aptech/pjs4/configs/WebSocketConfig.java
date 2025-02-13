package fpt.aptech.pjs4.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Đặt prefix cho các tin nhắn từ client
        registry.enableSimpleBroker("/queue", "/topic");  // Để gửi tin nhắn đến client
        registry.setApplicationDestinationPrefixes("/app"); // Để gửi tin nhắn từ client đến server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:5173") // Cho phép yêu cầu từ frontend
                .withSockJS();
    }
}
