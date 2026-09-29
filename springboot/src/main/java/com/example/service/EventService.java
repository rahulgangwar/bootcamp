package com.example.service;

import com.example.dto.Event;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class EventService {

    private final BlockingQueue<Event> events = new LinkedBlockingQueue<>();

    public void addEvent(String message) {
        Event event = new Event(UUID.randomUUID().toString(), message);
        events.offer(event);
    }

    public Event getEvent() throws InterruptedException {
        return events.take();
    }
}
