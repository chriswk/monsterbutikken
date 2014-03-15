package no.borber.monsterShop.store;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class EventStore {
    private List<MonsterEvent> events;

    public EventStore() {
        this.events = new ArrayList<>();
    }
    public void append(MonsterEvent event) {
        events.add(event);
    }

    public Iterable<MonsterEvent> getEvents() {
        return events;
    }

    public Optional<MonsterEvent> getById(final String rootId) {
        return Iterables.tryFind(events, new Predicate<MonsterEvent>() {
            @Override
            public boolean apply(MonsterEvent monsterEvent) {
                return monsterEvent.getRootId().equals(rootId);
            }
        });
    }
}
