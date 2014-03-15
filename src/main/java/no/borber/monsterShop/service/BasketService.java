package no.borber.monsterShop.service;

import java.util.UUID;

import no.borber.monsterShop.basket.BasketItem;
import no.borber.monsterShop.command.BasketAddItemCommand;
import no.borber.monsterShop.command.BasketAggregate;
import no.borber.monsterShop.command.BasketRemoveItemCommand;
import no.borber.monsterShop.store.EventStore;
import no.borber.monsterShop.store.MonsterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    private EventStore eventStore;

    public String create() {
        return UUID.randomUUID().toString();
    }

    public void add(String id, String monsterType) {
        MonsterEvent addEvent = fetch(id).handle(new BasketAddItemCommand(monsterType));
        eventStore.append(addEvent);
    }

    protected BasketAggregate fetch(String id) {
        BasketAggregate aggregate = new BasketAggregate(id);
        for (MonsterEvent event : eventStore.getEvents(id)) {
            aggregate.handle(event);
        }
        return aggregate;
    }

    public void setEventStore(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public EventStore getEventStore() {
        return eventStore;
    }

    public void remove(String id, String monsterType) {
        MonsterEvent removeEvent = fetch(id).handle(new BasketRemoveItemCommand(monsterType));
        eventStore.append(removeEvent);
    }
}
