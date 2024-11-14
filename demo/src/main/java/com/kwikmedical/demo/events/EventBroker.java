package com.kwikmedical.demo.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class EventBroker {

    private final HashMap<Class<?>, List<Consumer<?>>> subscribers = new HashMap<>();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T> void publish(T event) {
        List<Consumer<?>> listeners = subscribers.get(event.getClass());
        if (listeners != null) {
            for (Consumer<?> listener : listeners) {
                Consumer<T> typedListener = (Consumer<T>) listener;
                executorService.submit(() -> typedListener.accept(event));
            }
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
