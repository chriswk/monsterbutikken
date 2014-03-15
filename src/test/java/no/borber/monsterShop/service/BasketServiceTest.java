package no.borber.monsterShop.service;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import no.borber.monsterShop.basket.BasketItem;
import no.borber.monsterShop.command.BasketAggregate;
import no.borber.monsterShop.store.AddBasketItemEvent;
import no.borber.monsterShop.store.EventStore;
import no.borber.monsterShop.store.MonsterEvent;
import no.borber.monsterShop.store.MonsterProjection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasketServiceTest {
    BasketService service;
    EventStore store;

    @Before
    public void setUp() {
        store = new EventStore();
        service = new BasketService();
        service.setEventStore(store);
    }

    @Test
    public void should_create_new_basket_id() {
        String aggregateId = service.create();
        final List<MonsterEvent> events = new ArrayList<>();
        store.subscribe(new MonsterProjection(aggregateId) {
            @Override
            public void update(MonsterEvent event) {
                events.add(event);
            }
        });
        service.add(aggregateId, "Hydra");
        assertFalse(events.isEmpty());
        assertTrue(Iterables.all(events, new Predicate<MonsterEvent>() {
            @Override
            public boolean apply(MonsterEvent monsterEvent) {
                return monsterEvent.getType().equals("basket");
            }
        }));
    }

    @Test
    public void should_remove_basket_item() {
        String aggregateId = service.create();
        service.add(aggregateId, "Hydra");
        service.remove(aggregateId, "Hydra");
        assertTrue(service.fetch(aggregateId).getItems().isEmpty());
    }

    @Test
    public void should_replay_events() {
        String aggregateId = service.create();
        store.append(new AddBasketItemEvent(aggregateId, "basket", "Hydra"));
        store.append(new AddBasketItemEvent(aggregateId, "basket", "Sfinks"));
        BasketAggregate aggregate = service.fetch(aggregateId);
        List<BasketItem> items = aggregate.getItems();
        assertEquals(2, items.size());
        assertTrue(Iterables.any(items, new Predicate<BasketItem>() {
            @Override
            public boolean apply(BasketItem input) {
                return input.getMonsterType().equals("Hydra");
            }
        }));
        assertTrue(Iterables.any(items, new Predicate<BasketItem>() {
            @Override
            public boolean apply(BasketItem input) {
                return input.getMonsterType().equals("Sfinks");
            }
        }));

    }


}
