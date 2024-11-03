package homeTry.chatting.config;

import homeTry.chatting.exception.handler.StompInterceptorErrorHandler;
import homeTry.chatting.interceptor.StompInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChattingConfig implements WebSocketMessageBrokerConfigurer {
    private final StompInterceptor stompInterceptor;
    private final StompInterceptorErrorHandler stompInterceptorErrorHandler;

    public ChattingConfig(StompInterceptor stompInterceptor,
            StompInterceptorErrorHandler stompInterceptorErrorHandler) {
        this.stompInterceptor = stompInterceptor;
        this.stompInterceptorErrorHandler = stompInterceptorErrorHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/chatting/websocket")
                .setAllowedOrigins("http://localhost:63342", "http://localhost:3000", "https://localhost:3000")
                .withSockJS()
                .setWebSocketEnabled(true)
                .setSessionCookieNeeded(false);

        registry.setErrorHandler(stompInterceptorErrorHandler);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.setApplicationDestinationPrefixes("/pub")
                .setUserDestinationPrefix("/user")
                .enableSimpleBroker("/sub", "/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompInterceptor);
    }
}