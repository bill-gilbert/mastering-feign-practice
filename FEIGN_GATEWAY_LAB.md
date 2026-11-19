# Feign + Gateway + Circuit Breaker: учебный стенд

Maven-модули образуют один небольшой стенд:

```text
curl/client -> caller-service :18081 -> OpenFeign -> api-gateway :18082 -> callee-service :18080
                                                    \-> Resilience4j circuit breaker + fallback
```

`callee-service` специально умеет работать в режимах `success`, `fail` (500),
`unavailable` (503), `slow` (3 секунды) и `flaky`. Шлюз убирает префикс
`/callee`, добавляет заголовки `X-Gateway` и проксирует запрос. Feign по умолчанию
ходит именно через него. Чтобы сравнить с прямым вызовом, перед стартом caller
задайте `CALLEE_URL=http://localhost:18080`.

## Запуск (PowerShell)

В трёх отдельных окнах из корня репозитория:

```powershell
mvn -f callee-service/pom.xml spring-boot:run
mvn -f api-gateway/pom.xml spring-boot:run
mvn -f client-retry-circuitbreaker/pom.xml spring-boot:run
```

Проверка успешного пути:

```powershell
irm http://localhost:18081/api/v1/demo/callee?mode=success
```

## Как увидеть размыкание

1. Четыре раза вызовите `fail` или `unavailable`. Первые вызовы доходят до callee и
   возвращают JSON с `fallback: true`.

   ```powershell
   1..4 | % { irm "http://localhost:18081/api/v1/demo/callee?mode=unavailable" }
   ```

2. После четвёртого вызова failure rate равен 100%, breaker `calleeService`
   переходит в `OPEN`. Последующие вызовы возвращаются сразу с причиной
   `circuit breaker is OPEN; Feign did not call the gateway` — фактического HTTP
   вызова нет.

3. Состояние и события видны в Actuator:

   ```powershell
   irm http://localhost:18081/actuator/circuitbreakers
   irm http://localhost:18081/actuator/circuitbreakerevents
   ```

Через 10 секунд breaker перейдёт в `HALF_OPEN` и разрешит два пробных запроса.
Вызовите `success` дважды: он снова станет `CLOSED`. Для эмуляции настоящего
отказа инфраструктуры можно остановить `callee-service` или `api-gateway`; после
нескольких таймаутов/ошибок соединения результат будет тем же.

## Важные места для экспериментов

| Что менять | Где | Эффект |
| --- | --- | --- |
| Размер окна, порог, half-open | `client-retry-circuitbreaker/src/main/resources/application.yml` | Когда breaker открывается и проверяет восстановление |
| Таймауты Feign | тот же файл, `spring.cloud.openfeign.client.config` | Режим `slow` будет ошибкой либо пройдёт |
| Точка назначения Feign | `CALLEE_URL` | Сравнение gateway и прямого вызова |
| Gateway filters/route | `api-gateway/src/main/resources/application.yml` | Переписывание пути, заголовки, роутинг |
| Имя breaker | `CircuitBreakerConfiguration` | Должно совпадать с `resilience4j.circuitbreaker.instances.calleeService` |

Retry намеренно выключен (`Retryer.NEVER_RETRY`), чтобы каждый неуспешный вызов
был виден в статистике breaker. Его стоит включать отдельным опытом: ретраи могут
увеличить нагрузку на уже недоступный сервис.
