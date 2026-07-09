package com.example.caller.client;

import com.example.caller.controller.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CalleeFallbackFactory implements FallbackFactory<CalleeClient> {
    private static final Logger log = LoggerFactory.getLogger(CalleeFallbackFactory.class);

    @Override
    public CalleeClient create(Throwable cause) {
        // 1. Логируем причину падения сервиса (это самое полезное, что дает FallbackFactory)
        log.error("Сбой вызова CalleeClient. Причина: {}", cause.getMessage(), cause);

        // 2. Возвращаем анонимную реализацию интерфейса CalleeClient с логикой "заглушки"
        return new CalleeClient() {

            @Override
            public ResponseData getResponseWithMode(String mode) {
                log.warn("Сработал fallback для метода getResponseWithMode с параметром mode={}", mode);
                return buildFallbackResponse("Сервис временно недоступен (getResponseWithMode)");
            }

            @Override
            public ResponseData getResponse(String mode) {
                log.warn("Сработал fallback для метода getResponse с параметром mode={}", mode);
                return buildFallbackResponse("Сервис временно недоступен (getResponse)");
            }

            // Вспомогательный метод для создания дефолтного ответа
            private ResponseData buildFallbackResponse(String errorMessage) {
                // TODO: Адаптируйте этот код под реальную структуру вашего класса ResponseData
                // Например, если у вас есть Builder или конструктор:
                // response.setData(null);
                return new ResponseData("FALLBACK");
            }
        };
    }
}