package no.borber.monsterShop.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Service;

@Service
public class EventStore {
    private Map<String, List<MonsterEvent>> events;
    private EventBus eventBus;

    public EventStore() {
        this.events = new HashMap<>();
        this.eventBus = new EventBus();
    }

    public List<MonsterEvent> getEvents(String rootId) {
        return Optional.fromNullable(events.get(rootId)).or(new ArrayList<MonsterEvent>());
    }

    public void append(MonsterEvent event) {
        if (!events.containsKey(event.getRootId())) {
            events.put(event.getRootId(), new ArrayList<MonsterEvent>());
        }
        events.get(event.getRootId()).add(event);
        eventBus.post(event);
    }


    public void subscribe(MonsterProjection observer) {
        observer.catchUp(events.get(observer.getRootId()));
        eventBus.register(observer);
    }
}

