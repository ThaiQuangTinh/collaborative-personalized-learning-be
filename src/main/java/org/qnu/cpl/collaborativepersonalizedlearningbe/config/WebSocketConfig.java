package org.qnu.cpl.collaborativepersonalizedlearningbe.config;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.JwtTokenUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtTokenUtil jwtTokenUtil;

    private final CorsProperties corsProperties;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        String uiHost = corsProperties.getAllowedOrigins().get(0);

        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns(uiHost)
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(
                            ServerHttpRequest request,
                            WebSocketHandler wsHandler,
                            Map<String, Object> attributes) {

                        String token = null;

                        if (request instanceof ServletServerHttpRequest servletRequest) {
                            token = servletRequest.getServletRequest().getParameter("token");
                        }

                        if (token == null || token.isBlank() || token.equals("null")) {
                            return null;
                        }

                        String userId = jwtTokenUtil.getUserIdFromToken(token);

                        return () -> userId;
                    }
                })
                .withSockJS();
    }

}
