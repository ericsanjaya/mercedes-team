package com.mercedes.amg.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriTemplate;

import java.util.AbstractMap;
import java.util.Map;


@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketConfig implements WebSocketConfigurer {
    final Map<String, String> uri = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("car", "/ws/car/{vehicleId}"),
            new AbstractMap.SimpleEntry<>("dashboard", "/ws/dashboard/{vehicleId}")
    );

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), uri.get("car"))
                .addInterceptors(interceptorCar())
                .setAllowedOrigins("*");

        registry.addHandler(webSocketHandler(), uri.get("dashboard"))
                .addInterceptors(interceptorDashboard())
                .setAllowedOrigins("*");
    }

    @Bean
    public HandshakeInterceptor interceptorCar() {
        return new HandshakeInterceptor() {
            private final UriTemplate URI_TEMPLATE = new UriTemplate(uri.get("car"));

            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                var subProtocols = request.getHeaders().get("sec-websocket-protocol");
                if (subProtocols == null || subProtocols.size() < 1) {
                    return false;
                }

                // Get the URI segment corresponding to the auction id during handshake
                Map<String, String> segments = URI_TEMPLATE.match(request.getURI().getPath());

                // This will be added to the websocket session
                var a = segments.get("vehicleId");
                if (a != null) {
                    attributes.put("vehicleId", segments.get("vehicleId"));
                }

                attributes.put("type", "car");
                return true;
            }

            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Exception exception) {
                // Nothing to do after handshake
            }
        };
    }

    @Bean
    public HandshakeInterceptor interceptorDashboard() {
        return new HandshakeInterceptor() {
            private final UriTemplate URI_TEMPLATE = new UriTemplate(uri.get("dashboard"));

            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                var subProtocols = request.getHeaders().get("sec-websocket-protocol");
                if (subProtocols == null || subProtocols.size() < 1) {
                    return false;
                }

                // Get the URI segment corresponding to the auction id during handshake
                Map<String, String> segments = URI_TEMPLATE.match(request.getURI().getPath());

                // This will be added to the websocket session
                var a = segments.get("vehicleId");
                if (a != null) {
                    attributes.put("vehicleId", segments.get("vehicleId"));
                }

                attributes.put("type", "dashboard");
                return true;
            }

            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Exception exception) {
                // Nothing to do after handshake
            }
        };
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new AMGWebSocketHandler();
    }
}
