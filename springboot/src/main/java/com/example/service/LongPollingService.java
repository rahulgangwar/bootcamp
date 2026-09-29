package com.example.service;

import com.example.dto.Event;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LongPollingService {

    private final ConcurrentMap<String, DeferredResult<Event>> clients = new ConcurrentHashMap<>();

    public DeferredResult<Event> waitForEvent() {
        String clientId = UUID.randomUUID().toString();

        // Wait maximum 30 seconds
        DeferredResult<Event> deferredResult = new DeferredResult<>(30_000L);

        clients.put(clientId, deferredResult);

        // Client timeout
        deferredResult.onTimeout(
                () -> {
                    clients.remove(clientId);
                    deferredResult.setResult(null);
                });

        // Client completed normally
        deferredResult.onCompletion(
                () -> {
                    clients.remove(clientId);
                });

        return deferredResult;
    }

    public void publishEvent(Event event) {
        clients.forEach(
                (clientId, deferredResult) -> {
                    deferredResult.setResult(event);
                    clients.remove(clientId);
                });
    }
}
