package fpt.aptech.pjs4.configs;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        // Giả sử userId được gửi trong query param: ws://localhost:8080/ws?userId=31
        String query = request.getURI().getQuery();

        if (query != null && query.contains("userId=")) {
            String userId = query.split("userId=")[1];
            attributes.put("userId", userId); // Lưu userId vào attributes
            System.out.println("✅ Lưu userId vào attributes: " + userId);
        } else {
            System.out.println("⚠️ Không tìm thấy userId trong query");
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Không cần xử lý sau handshake
    }
}
