package com.slmas.Sl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://172.20.20.69", "http://localhost:5173", "http://181.88.177.21") // Permitir el origen de tu frontend
                .withSockJS(); // Usa SockJS como fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Configura el broker de mensajes para el prefijo /topic
        registry.setApplicationDestinationPrefixes("/app"); // Prefijo para las rutas de los mensajes del cliente
    }
}

