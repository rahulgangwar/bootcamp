package com.example.controller;

import com.example.dto.Event;
import com.example.service.LongPollingService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/events")
public class EventController {

    private final LongPollingService longPollingService;

    public EventController(LongPollingService longPollingService) {
        this.longPollingService = longPollingService;
    }

    @GetMapping
    public DeferredResult<Event> waitForEvent() {
        return longPollingService.waitForEvent();
    }

    @PostMapping
    public String publishEvent(@RequestParam String message) {
        Event event = new Event(java.util.UUID.randomUUID().toString(), message);
        longPollingService.publishEvent(event);
        return "Event published";
    }
}
