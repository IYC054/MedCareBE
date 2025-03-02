package fpt.aptech.pjs4.configs;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = (String) attributes.get("userId");

        if (userId == null) {
            System.out.println("⚠️ Không tìm thấy userId trong session!");
            return null;
        }

        System.out.println("✅ Gán Principal: userId = " + userId);
        return new StompPrincipal(userId); // StompPrincipal là class bạn cần tạo
    }
}
