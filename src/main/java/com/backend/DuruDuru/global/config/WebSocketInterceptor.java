package com.backend.DuruDuru.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

    @Component
    @Slf4j
    public class WebSocketInterceptor implements ChannelInterceptor {

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
            // 구독의 경우 destination 로그 출력
            if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
                log.info("Destination: {}", accessor.getDestination());
            }
            return message;
        }
    }
